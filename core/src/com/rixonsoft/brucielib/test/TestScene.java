package com.rixonsoft.brucielib.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rixonsoft.brucielib.scene.BasicScene;

/** Example scene.
 *
 *  Steps to create a new scene:
 *
 *  1. Subclass BasicScene with reference field for the assets. (AssetBag)
 *
 *  2. Call wrangleAssetBag() in preload() method.
 *
 *  3. Implement start() method to resolve assets and set up scene.
 *
 *  4. Implement draw() method.
 *
 *  5. Implement resize() ??
 */
public class TestScene extends BasicScene {
    private static final String TAG = "TESTSCENE";
    private TestBundle bundle;

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    private SpriteBatch batch;
    private float sx, sy;
    private Stage stage;

    /** Called after Scene is instantiated to allow queueing of asset loads.
     *
     */
    @Override
    public void preload() {
        // Schedule your AssetBag for loading.
        bundle = wrangleAssetBag(TestBundle.class);
        Gdx.app.log(TAG,"loading bundle");
    }

    /** Called when the scene starts.
     *
     */
    @Override
    public void start() {
        Gdx.app.log(TAG,"showing scene");

        // You need to call resolveAssets before using the AssetBag.
        bundle.resolveAssets();


        // Set up basic example libGDX scene, camera and batch
        OrthographicCamera c = new OrthographicCamera();
        c.setToOrtho(false, WIDTH, HEIGHT);
        batch = new SpriteBatch();
        addDisposable(batch);

        // Viewpoart and stage.
        Viewport vp = new FitViewport(WIDTH, HEIGHT, c);
        stage = new Stage(vp,batch);
        Gdx.input.setInputProcessor(stage);
        addDisposable(stage);

        // .. with a useless button.
        Button b = new TextButton("Button",bundle.defaultSkin);
        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myGame.queueScene(new TestScene());
                actor.setTouchable(Touchable.disabled);
                //stateMachine.changeState(BasicFadeyState.FADEOUT);
                setFadeOut();
            }
        });

        stage.addActor(b);

        sx = WIDTH/2f - bundle.brucieLogo.getWidth()/2f;
        sy = HEIGHT/2f - bundle.brucieLogo.getHeight()/2f;
    }

    /** Called at every frame draw.
     *
     * @param delta milliseconds since last frame.
     */
    @Override
    public void draw(float delta) {
        // TODO: Audit usage of viewport.apply()
        stage.getViewport().apply();

        // Clear screen
        Gdx.gl20.glClearColor(1f,1f,1f,1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw logo
        batch.begin();
        batch.draw(bundle.brucieLogo,sx,sy);
        batch.end();

        // Run stage/UI handler.
        stage.act(delta);
        stage.draw();
    }



    //---- Standard LibGDX Screen methods ---

    @Override
    public void resize( int width, int height) {
        super.resize(width,height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        // These are not needed because of addDisposable() call in start()
        //if(stage != null) stage.dispose();
        //if(batch != null) batch.dispose();
        super.dispose();
    }


    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
