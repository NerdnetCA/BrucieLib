package com.rixonsoft.brucielib.tufree.retron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;

public class RetronBuffer implements Disposable {

    private final int width;
    private final int height;
    private final FrameBuffer framebuffer;
    private final OrthographicCamera camera;
    private final SpriteBatch bufferBatch;
    private final DisplayFilter displayFilter;
    private float bgRed, bgGreen, bgBlue;

    public RetronBuffer(int width, int height) {
        this.width = width;
        this.height = height;

        // Video framebuffer of exactly the specified resolution
        framebuffer = new FrameBuffer(Pixmap.Format.RGBA8888,width,height,false);

        // Main camera for rendering the video framebuffer
        camera = new OrthographicCamera();
        camera.setToOrtho(true,width,height);

        // Main rendering bufferBatch
        bufferBatch = new SpriteBatch();
        bufferBatch.setProjectionMatrix(camera.combined);

        // OpenGL shader for filtering the framebuffer when rendering to display
        displayFilter = new DisplayFilter();

        // Set default filter parameters
        displayFilter.setBrite(1.4f);
        displayFilter.setSaturation(0.8f);

    }

    public void begin() {
        framebuffer.begin();
        Gdx.gl20.glClearColor(bgRed,bgGreen,bgBlue,1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        bufferBatch.begin();
    }
    public void end() {
        bufferBatch.end();
        framebuffer.end();
    }

    public void draw(Batch batch) {
        batch.begin();
        batch.setShader(displayFilter.getShaderProgram());
        displayFilter.setUniforms();
        batch.draw(framebuffer.getColorBufferTexture(),0,0);
        batch.end();
    }

    public void dispose() {
        framebuffer.dispose();
        bufferBatch.dispose();
        displayFilter.dispose();
    }
}
