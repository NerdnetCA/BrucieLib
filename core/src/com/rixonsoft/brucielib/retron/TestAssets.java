package com.rixonsoft.brucielib.retron;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rixonsoft.brucielib.AssetBag;

public class TestAssets extends AssetBag {

    public static final String DUMMY = "brucie/logo.png";
    public static final String SKIN = "brucie/ui/cuticle_alphablue.json";

    public Texture asset;
    public Texture dummy;
    public Skin skin;

    @Override
    public void resolveAssets() {
        dummy = assetManager.get(DUMMY, Texture.class);
        skin = assetManager.get(SKIN,Skin.class);
    }

    @Override
    public void queueAssets() {
        assetManager.load(DUMMY,Texture.class);
        assetManager.load(SKIN,Skin.class);
    }
}
