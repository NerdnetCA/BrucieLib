package com.rixonsoft.brucielib;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.rixonsoft.brucielib.boot.LoadingScreen;
import com.rixonsoft.brucielib.boot.SplashScreen;
import com.rixonsoft.brucielib.scene.Scene;

/** Base class for game entrypoint.
 *
 * Your game should subclass this with a stub, such as GameSelector.
 */
public abstract class BrucieGame implements ApplicationListener {
    private static final String TAG = "BRUCIEGAME"; // debug tag

    // The global config and asset manager instances.
    // Classes within your game may need to be able to find these,
    // therefore they are public
    public BrucieConfig brucieConfig;
    public AssetManager assetManager;

    // BasicScene management
    protected SplashScreen splashScene;
    protected Scene currentScene, nextScene;

    protected StateMachine<BrucieGame, GameState> gameStateMachine;

    // Internals
    private boolean booted;

    /** Called at game startup
     *
     */
    @Override
    public void create() {
        // Set up the config object and asset manager, with tilemap loading.
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

        // wait until assets are loaded.
        assetManager.finishLoading();

        // Show the splash.
        splashScene.show();
        splashScene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /** Set the entrypoint scene of the game.
     * This can only be called once, after the call to create()
     *
     * @param bootscene Instantiated and Wrangled Scene.
     */
    public void bootScene(Scene bootscene) {
        if(!booted) {
            nextScene = bootscene;
            nextScene.configure(this);
            nextScene.preload();
            booted = true;
        }
    }

    /** Set a Scene to be run after the current one
     * finishes.
     *
     * @param scene Instantiated and Wrangled Scene
     */
    public void queueScene(Scene scene) {
        if(nextScene != null) {
            // The queue has a size limit of one.
        } else {
            nextScene = scene;
            gameStateMachine.changeState(GameState.PRELOAD);
        }
    }

    /** Advance to the next Scene, disposing the current scene.
     *
     */
    public void toNextScene() {
        if(currentScene != null)
            currentScene.hide();
        currentScene = nextScene;
        currentScene.show();
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        nextScene = null;
    }

    /** render call - this is called to draw frames.
     * Simply call update on the state machine.
     */
    public void render() {
        gameStateMachine.update();
    }


    // ------ Other stuff.. don't worry about what's below here -----


    /** LibGDX method - called when screen resizes.
     *
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        if(currentScene != null) {
            currentScene.resize(width,height);
        }
    }

    /** LibGDX method - important for Android target
     *
     */
    public void pause() {}

    /** LibGDX method - important for Android target
     *
     */
    public void resume() {}


    /** Dispose the game.
     *
     */
    public void dispose() {
        // Dump scenes.
        if(currentScene != null) currentScene.dispose();
        if(nextScene != null) nextScene.dispose();
        // Dump all assets.
        assetManager.dispose();
    }

    /** The main game state machine.
     *
     */
    public static enum GameState implements State<BrucieGame> {

        BOOTSPLASH() {
            @Override
            public void update(BrucieGame game) {
                game.splashScene.draw(Gdx.graphics.getDeltaTime());
                if(game.assetManager.update() && game.splashScene.isDone()) {
                    game.toNextScene();
                    game.gameStateMachine.changeState(GameState.NORMAL_RUN);
                }
            }
        },

        PRELOAD() {
            @Override
            public void update(BrucieGame game) {
                super.update(game);
                boolean loaded = game.assetManager.update();
                if(loaded) {
                    if(game.currentScene.isDone()) {
                        game.toNextScene();
                        game.gameStateMachine.changeState(NORMAL_RUN);
                    } else {
                        game.gameStateMachine.changeState(GameState.CUE);
                    }
                } else {
                    if(game.currentScene.isDone()) {
                        game.currentScene.hide();
                        game.currentScene = new LoadingScreen();
                        game.currentScene.configure(game);
                        game.currentScene.show();
                        game.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                        game.gameStateMachine.changeState(GameState.LOADING);
                    }
                }
            }

            @Override
            public void enter(BrucieGame game) {
                game.nextScene.configure(game);
                game.nextScene.preload();
            }
        },

        CUE() {
            @Override
            public void update(BrucieGame game) {
                super.update(game);
                if(game.currentScene.isDone()) {
                    game.toNextScene();
                    game.gameStateMachine.changeState(GameState.NORMAL_RUN);
                }
            }
        },

        LOADING() {
            @Override
            public void update(BrucieGame game) {
                super.update(game);
                if(game.assetManager.update()) {
                    game.toNextScene();
                    game.gameStateMachine.changeState(GameState.NORMAL_RUN);
                }
            }
        },

        NORMAL_RUN() {
        };

        @Override
        public void update(BrucieGame game) {
            game.currentScene.render(Gdx.graphics.getDeltaTime());
        }

        @Override
        public void enter(BrucieGame game) {

        }

        @Override
        public void exit(BrucieGame game) {

        }

        @Override
        public boolean onMessage(BrucieGame game, Telegram tele) {
            return false;
        }

    }
}
