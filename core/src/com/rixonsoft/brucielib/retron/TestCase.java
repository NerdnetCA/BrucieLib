package com.rixonsoft.brucielib.retron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rixonsoft.brucielib.scene.BasicScene;

public class TestCase extends BasicScene {


    private TestAssets assets;
    private RetronOld retron;
    private RetronDummy logo;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private HUD hud;

    @Override
    public void start() {
        super.show();

        assets.resolveAssets();

        retron = new RetronOld( 1f/60f , 320, 200);
        retron.setBackground(1f,1f,1f);

        wrangler.add(retron);

        logo = new RetronDummy().setTexture(assets.dummy);
        logo.addAction(
                Actions.rotateBy(360,5f)
        );

        retron.root.addActor(logo);

        hud = new HUD(1920,1080, assets.skin);

        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, 800, 600);

        //batch = wrangler.wrangle(SpriteBatch.class);
        //batch.setProjectionMatrix(camera.combined);

    }



    @Override
    public void draw(float delta) {


        retron.run(delta);
        retron.draw();

        hud.draw();

    }

    @Override
    public void preload() {
        assets = wrangler.wrangle(TestAssets.class);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    private static final String TAG = "TESTCASE";
}
