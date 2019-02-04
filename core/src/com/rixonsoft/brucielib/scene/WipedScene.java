package com.rixonsoft.brucielib.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rixonsoft.brucielib.AbstractScene;
import com.rixonsoft.brucielib.BrucieConfig;

public abstract class WipedScene extends AbstractScene {

    private static final float WIPESPEED = 1f/3f;

    private FrameBuffer myFB;
    private ShaderProgram shaderProgram;

    protected Stage gameStage;
    protected Stage guiStage;

    protected StateMachine<WipedScene, WipedState> wipeStateMachine;

    protected float wipeTime;

    private Viewport gameVp;
    private SpriteBatch guiBatch;
    private SpriteBatch renderBatch;
    private OrthographicCamera camera;
    private float mh, mw;

    private int BRITE;
    private int GRAY;

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                BrucieConfig.guiWidth,
                BrucieConfig.guiHeight);
        // Create a frame buffer - in this case, we do this to make applying
        // the opengl shader a little bit cleaner.
        myFB = new FrameBuffer(Pixmap.Format.RGBA8888,
                BrucieConfig.guiWidth,
                BrucieConfig.guiHeight,
                false);

        // Read in the vertex and fragment shader programs
        String vertexShader = Gdx.files.internal("brucie/glsl/vert_nul.glsl").readString();
        String fragmentShader = Gdx.files.internal("brucie/glsl/frag_satval.glsl").readString();
        // Compile shader program from vertex and fragment shaders
        shaderProgram = new ShaderProgram( vertexShader, fragmentShader);
        // Sadly, we really do need to set this, because pedantic mode
        // is absolutely ridiculous.
        shaderProgram.pedantic = false;
        BRITE = shaderProgram.getUniformLocation("brite");
        GRAY = shaderProgram.getUniformLocation("gray");

        wrangler.add(myFB);
        wrangler.add(shaderProgram);

        guiBatch = new SpriteBatch();
        guiBatch.setProjectionMatrix(camera.combined);
        gameVp = new StretchViewport(
                1920,1080,
                camera
        );
        gameStage = new Stage(gameVp,guiBatch);
        guiStage = new Stage(gameVp,guiBatch);
        Gdx.input.setInputProcessor(guiStage);
        //gameVp.update(BrucieConfig.guiWidth,BrucieConfig.guiHeight);

        renderBatch = new SpriteBatch(10,shaderProgram);
        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(true,1920,1080);
        renderBatch.setProjectionMatrix(cam.combined);

        wipeStateMachine = new DefaultStateMachine<WipedScene, WipedState>(
                this,
                WipedState.WIPEIN);

    }

    public abstract void draw(float delta);

    @Override
    public void render(float delta) {

        wipeTime += delta * WIPESPEED;
        gameStage.act(delta);
        guiStage.act(delta);

        myFB.begin();
        //camera.update();
        gameStage.draw();
        guiStage.draw();
        wipeStateMachine.update();
        myFB.end(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        //Gdx.gl20.glViewport((Gdx.graphics.getWidth()-(int)mw)/2,(Gdx.graphics.getHeight()-(int)mh)/2,(int)mw,(int)mh);

        renderBatch.begin();
        shaderProgram.setUniformf(BRITE, 1.0f-wipeStateMachine.getCurrentState().getFadeValue(wipeTime));
        shaderProgram.setUniformf(GRAY,1.0f-wipeStateMachine.getCurrentState().getFadeValue(wipeTime));
        renderBatch.draw(myFB.getColorBufferTexture(),0,0);
        renderBatch.end();


    }

    @Override
    public void resize(int width, int height) {
        guiStage.getViewport().update(width,height);
        Vector2 v = Scaling.fit.apply(1920,1080,width,height);
        mh = v.y;
        mw = v.x;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
