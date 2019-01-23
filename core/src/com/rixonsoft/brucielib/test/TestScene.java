package com.rixonsoft.brucielib.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rixonsoft.brucielib.AssetBundler;
import com.rixonsoft.brucielib.Scene;
import com.rixonsoft.brucielib.test.TestBundle;

public class TestScene extends Scene {
    AssetBundler<TestBundle> bundler;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Texture logoTexture;

    private SpriteBatch batch;
    private float sx, sy;
    private Stage stage;
    private Skin skin;

    @Override
    public void preload() {
        AssetBundler<TestBundle> a = wrangler.wrangle(AssetBundler.class);
        a.loadBundle(TestBundle.BRUCIELOGO);
        bundler = a;
    }

    @Override
    public void start() {
        logoTexture = (Texture)bundler.getAsset(TestBundle.BRUCIELOGO);
        skin = (Skin)bundler.getAsset(TestBundle.DEFAULTSKIN);

        OrthographicCamera c = new OrthographicCamera();
        c.setToOrtho(false, WIDTH, HEIGHT);
        batch = new SpriteBatch();

        Viewport vp = new FitViewport(WIDTH, HEIGHT, c);
        stage = new Stage(vp,batch);
        Gdx.input.setInputProcessor(stage);



        sx = WIDTH/2f - logoTexture.getWidth()/2f;
        sy = HEIGHT/2f - logoTexture.getHeight()/2f;
    }

    @Override
    public void draw(float delta) {
        Gdx.gl20.glClearColor(1f,1f,1f,1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(logoTexture,sx,sy);
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
