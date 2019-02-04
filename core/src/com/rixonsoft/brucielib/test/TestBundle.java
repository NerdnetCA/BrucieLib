package com.rixonsoft.brucielib.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rixonsoft.brucielib.AssetBag;

public class TestBundle extends AssetBag {

    private static final String DEFAULTSKIN = "brucie/ui/cuticle_alphablue.json";
    private static final String BRUCIELOGO = "brucie/logo.png";

    public Skin defaultSkin;
    public Texture brucieLogo;

    @Override
    public void resolveAssets() {
        defaultSkin = assetManager.get(DEFAULTSKIN,Skin.class);
        brucieLogo = assetManager.get(BRUCIELOGO,Texture.class);
    }

    @Override
    public void queueAssets() {
        assetManager.load(DEFAULTSKIN,Skin.class);
        assetManager.load(BRUCIELOGO, Texture.class);
    }
}
