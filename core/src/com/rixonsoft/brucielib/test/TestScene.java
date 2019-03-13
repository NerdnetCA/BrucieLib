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

public class TestScene extends BasicScene {
    private static final String TAG = "TESTSCENE";
    private TestBundle bundle;

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    private SpriteBatch batch;
    private float sx, sy;
    private Stage stage;

    @Override
    public void preload() {
        bundle = wrangleAssetBag(TestBundle.class);
        Gdx.app.log(TAG,"loading bundle");
    }

    @Override
    public void start() {
        Gdx.app.log(TAG,"showing scene");

        bundle.resolveAssets();

        OrthographicCamera c = new OrthographicCamera();
        c.setToOrtho(false, WIDTH, HEIGHT);
        batch = new SpriteBatch();

        Viewport vp = new FitViewport(WIDTH, HEIGHT, c);
        stage = new Stage(vp,batch);
        Gdx.input.setInputProcessor(stage);


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

    @Override
    public void draw(float delta) {
        stage.getViewport().apply();
        Gdx.gl20.glClearColor(1f,1f,1f,1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(bundle.brucieLogo,sx,sy);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize( int width, int height) {
        super.resize(width,height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        if(stage != null) stage.dispose();
        if(batch != null) batch.dispose();
        super.dispose();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
