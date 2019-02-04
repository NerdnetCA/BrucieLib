package com.rixonsoft.brucielib.test2;

import com.rixonsoft.brucielib.BasicFadeyScene;

public class MyScene extends BasicFadeyScene {

    private TestAssets assets;

    @Override
    public void preload() {
        assets = wrangler.wrangle(TestAssets.class);
    }

    @Override
    public void draw(float delta) {

    }

    @Override
    public void show() {
        super.show();
        assets.resolveAssets();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
