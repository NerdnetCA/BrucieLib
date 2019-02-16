package com.rixonsoft.brucielib.retron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.utils.Disposable;

public class Retron implements Disposable {

    private final float timestep;
    private final int width, height;

    private float bgRed, bgBlue, bgGreen;
    private boolean smooth = true;

    public final RetronEntity root;

    private final OrthographicCamera camera;
    private final OrthographicCamera flipCamera;
    private final SpriteBatch batch;
    private final SpriteBatch filterBatch;
    private final FrameBuffer framebuffer;
    private final DisplayFilter displayFilter;

    private float accumulator;

    public Retron(float timestep, int width, int height) {
        this.timestep = timestep;
        this.width = width;
        this.height = height;

        // Video framebuffer of exactly the specified resolution
        framebuffer = new FrameBuffer(Pixmap.Format.RGBA8888,width,height,false);

        // Root entity to hold other objects in the scene.
        root = new RetronEntity();

        // Main camera for rendering the video framebuffer
        camera = new OrthographicCamera();
        camera.setToOrtho(true,width,height);

        // Flipped camera for rendering the buffer to the display
        flipCamera = new OrthographicCamera();
        flipCamera.setToOrtho(false,width,height);

        // Main rendering batch
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // OpenGL shader for filtering the framebuffer when rendering to display
        displayFilter = new DisplayFilter("brucie/glsl/vert_nul.glsl",
                "brucie/glsl/frag_retron.glsl");
        // Batch for above
        filterBatch = new SpriteBatch();
        filterBatch.setProjectionMatrix(flipCamera.combined);
        filterBatch.setShader(displayFilter.getShaderProgram());

        // Set default filter parameters
        displayFilter.setBrite(1.5f);
    }

    /** Set smoothing option : smoothed or pixellated.
     *
     * @param smooth Render with smoothing if true
     */
    public void setSmoothing(boolean smooth) {
        this.smooth = smooth;
        if(smooth) {
            framebuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            framebuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        }
    }

    /** Call this from main render loop.
     *
     * This method breaks the delta into framestep-sized units, calling each entity's
     * act() method for each step. The first step of each run is shorter by the same
     * amount as the previous run had leftover time of less than a framestep.
     *
     * It sounds confusing, but it means that act() is called with AT LEAST the frequency
     * of the timestep, while physics() is called EXACTLY at the frequency of the timestep,
     * and draw() is called once per render loop.
     *
     * @param delta delta time.
     */
    public void run(float delta) {
        float runtime = accumulator + delta;

        while(runtime > timestep) {
            root.act(timestep-accumulator);
            runtime -= timestep;
            delta = runtime;
            accumulator = 0f;
            root.physics();
        }
        root.act(delta);
        accumulator += delta;

        framebuffer.begin();
        Gdx.gl20.glClearColor(bgRed,bgGreen,bgBlue,1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        root.draw(batch, 1f);
        batch.end();
        framebuffer.end();

    }

    /** This is called by the Scene after run() to render the framebuffer
     * to the display.
     */
    public void draw() {
        filterBatch.begin();
        displayFilter.setUniforms();
        filterBatch.draw(framebuffer.getColorBufferTexture(),0,0);
        filterBatch.end();
    }

    /** deprecated.
     *
     * @return
     */
    public Texture getTexture() {
        return framebuffer.getColorBufferTexture();
    }

    /** Set background colour of this Retron.
     *
     * The retron will clear its frame buffer with this colour on each draw.
     *
     * @param r Red component 0 to 1f
     * @param g Green component
     * @param b Blue component
     */
    public void setBackground(float r, float g, float b) {
        this.bgRed = r;
        this.bgGreen = g;
        this.bgBlue = b;
    }




    @Override
    public void dispose() {
        if(batch != null) batch.dispose();
        if(framebuffer != null) framebuffer.dispose();
        if(filterBatch != null) filterBatch.dispose();
        if(displayFilter != null) displayFilter.dispose();
    }

    private static final String TAG = "RETRON";

}
