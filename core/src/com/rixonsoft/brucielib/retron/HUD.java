package com.rixonsoft.brucielib.retron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.rixonsoft.brucielib.BrucieGame;
import com.rixonsoft.brucielib.core.scene.Wrangled;

public class HUD implements Wrangled, Disposable {


    public final Stage stage;
    private final OrthographicCamera camera;
    private final FillViewport viewport;
    private final Skin skin;
    private final Slider slider;
    private final Slider slider2;
    private final Slider slider3;
    private final Slider slider4;
    private BrucieGame game;

    @Override
    public void dispose() {
        stage.dispose();
    }

    public float getSlider1() {
        return slider.getValue();
    }
    public float getSlider2() {
        return slider2.getValue();
    }
    public float getSlider3() {
        return slider3.getValue();
    }
    public float getSlider4() {
        return slider4.getValue();
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

        Table t = new Table(skin);
        t.align(Align.right);
        t.setFillParent(true);
        t.row().height(400f);
        slider = new Slider(0f,2f,0.1f,true,skin);
        slider.setValue(1f);
        slider.setHeight(400f);
        t.add(slider).height(400f);
        slider2 = new Slider(0f,2f,0.1f,true,skin);
        slider2.setHeight(400f);
        slider2.setValue(1f);
        t.add(slider2);
        slider3 = new Slider(0f,1.0f,0.02f,true,skin);
        slider3.setHeight(400f);
        slider3.setValue(0.8f);
        t.add(slider3);
        slider4 = new Slider(0f,0.8f,0.05f,true,skin);
        slider4.setHeight(400f);
        slider4.setValue(0.4f);
        t.add(slider4);
        //t.add(new Button(skin,"roundcheck"));
        t.pack();

        stage.addActor(t);
        Gdx.input.setInputProcessor(stage);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }
}
