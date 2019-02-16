package com.rixonsoft.brucielib.retron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.rixonsoft.brucielib.BrucieGame;
import com.rixonsoft.brucielib.Wrangled;

public class HUD implements Wrangled, Disposable {


    public final Stage stage;
    private final OrthographicCamera camera;
    private final FillViewport viewport;
    private final Skin skin;
    private BrucieGame game;

    @Override
    public void dispose() {
        stage.dispose();
    }



    @Override
    public void setGame(BrucieGame game) {
        this.game = game;
    }


    public HUD(int width, int height, Skin skin) {
        this.skin = skin;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width,height);
        viewport = new FillViewport(width,height,camera);
        stage = new Stage(viewport);

        stage.addActor(new TextButton("beep",skin));
        Gdx.input.setInputProcessor(stage);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }
}
