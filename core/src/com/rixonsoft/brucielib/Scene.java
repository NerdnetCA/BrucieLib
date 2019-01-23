package com.rixonsoft.brucielib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Iterator;

/**
 * Superclass for the scenes of the game.
 *
 *
 */


public abstract class Scene implements Screen, Disposable {
    private static final String TAG = "SCENE";

    public static final float WIPE_TIME = 0.9f;

    protected StateMachine<Scene, SceneState> stateMachine;

    protected boolean done;

    protected BrucieGame myGame;
    protected AssetManager assetManager;

    public Wrangler wrangler;

    // helps track asset loading.
    private Array<String> assetList;

    private ShapeRenderer fadeRenderer;
    private OrthographicCamera mCamera;
    private float mWidth=256f, mHeight=256f;
    private float wipeSpeedFactor;

    public float wipeTime;

    public void configure(BrucieGame game) {
        myGame = game;
        assetList = new Array<String>(false,16);
        assetManager = myGame.assetManager;
        wrangler = new Wrangler();
        wrangler.setAssetManager(assetManager);
        wrangler.setGame(game);
    }

    public abstract void preload();
    public abstract void draw(float delta);
    public abstract void start();

    /** Scenes are generally allowed to run until isDone returns false.
     * @return
     */
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    /** using loadAsset rather than the libgdx AssetManager directly,
     * allows the Brucie Scene class to do some management for you.
     *
     * @param name
     * @param assetType
     */
    public void loadAsset(String name, Class assetType) {
        myGame.assetManager.load(name, assetType);
        assetList.add(name);
    }

    /** loadAsset, but with parameter.
     *
     * @param name
     * @param assetType
     * @param param
     */
    public void loadAsset(String name, Class assetType, AssetLoaderParameters param) {
        myGame.assetManager.load(name, assetType, param);
        assetList.add(name);
    }

    /**
     * dispose really REALLY needs to be called for Scenes, in order to mark
     * assets for unloading. The hide method will do this.
     */
    @Override
    public void dispose() {
        Gdx.app.log(TAG, "Disposing Scene " + getClass().getName());
        Iterator<String> iter = assetList.iterator();
        while(iter.hasNext()) {
            myGame.assetManager.unload(iter.next());
        }
        wrangler.dispose();
    }

    /** hide will call dispose. See dispose. If you override this, make sure to call super.
     *
     */
    @Override
    public void hide() {
        dispose();
    }

    public void show() {
        wipeSpeedFactor = 1f/WIPE_TIME;
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false,mWidth,mHeight);
        mCamera.update();
        fadeRenderer = new ShapeRenderer();
        stateMachine = new DefaultStateMachine<Scene, SceneState>(this, SceneState.FADEIN);
        beginFade();
        start();
    }

    public void resize(int screenWidth, int screenHeight) {
        //mCamera.update(); // Is this needed??
    }

    public void beginFade() {
        wipeTime = 0f;
    }

    public void render(float delta) {
        stateMachine.update();
    }

    public boolean renderFade(float delta) {
        mCamera.update();
        wipeTime += (delta * wipeSpeedFactor);

        float fadeAmount = stateMachine.getCurrentState().getFadeValue(wipeTime);

        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        fadeRenderer.setProjectionMatrix(mCamera.combined);
        fadeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        fadeRenderer.setColor(0f, 0f, 0f, fadeAmount);
        fadeRenderer.rect(0f, 0f, mWidth, mHeight);
        fadeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        if(wipeTime > 1f) {
            return true;
        }
        return false;
    }

    public void setFadeIn() {
        stateMachine.changeState(SceneState.FADEIN);
    }

    public void setFadeOut() {
        stateMachine.changeState(SceneState.FADEOUT);
    }

}
