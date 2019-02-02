package com.rixonsoft.brucielib;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public abstract class BrucieGame implements ApplicationListener {
    private static final String TAG = "BRUCIEGAME";

    // The global config and asset manager instances.
    // Classes within your game generally need to be able to find these.
    public BrucieConfig brucieConfig;
    public AssetManager assetManager;

    // Scene management
    protected SplashScreen splashScene;
    protected Scene currentScene, nextScene;

    protected StateMachine<BrucieGame, GameState> gameStateMachine;

    // Internals
    private boolean booted;

    @Override
    public void create() {
        brucieConfig = new BrucieConfig();
        assetManager = new AssetManager();
        assetManager.setLoader(
                TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver())
        );
        gameStateMachine =
                new DefaultStateMachine<BrucieGame, GameState>(this, GameState.BOOTSPLASH);

        // load perma-assets
        assetManager.load(brucieConfig.loading_img,Texture.class);

        // load splash
        splashScene = new SplashScreen();
        splashScene.configure(this);
        splashScene.preload();

        assetManager.finishLoading();

        splashScene.show();
        splashScene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void bootScene(Scene bootscene) {
        if(!booted) {
            nextScene = bootscene;
            nextScene.configure(this);
            nextScene.preload();
            booted = true;
        }
    }

    public void queueScene(Scene scene) {
        if(nextScene != null) {
            // The queue has a size limit of one.
        } else {
            nextScene = scene;
            scene.configure(this);
            gameStateMachine.changeState(GameState.PRELOAD);
        }
    }
    public void toNextScene() {
        if(currentScene != null)
            currentScene.hide();
        currentScene = nextScene;
        currentScene.show();
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        nextScene = null;
    }
    public void render() {
        gameStateMachine.update();
    }

    @Override
    public void resize(int width, int height) {
        if(currentScene != null) {
            currentScene.resize(width,height);
        }
    }

    public void pause() {}

    public void resume() {}


    public void dispose() {
        assetManager.dispose();
    }
}
