package com.rixonsoft.brucielib.retron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rixonsoft.brucielib.core.scene.BasicScene;

public abstract class RetronScene extends BasicScene {

    private SpriteBatch batch;

    @Override
    public void draw(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //batch.draw(assets.logo,0,0);
        batch.end();
    }

    public void start() {
        batch = wrangle(SpriteBatch.class);
    }


}
