package com.rixonsoft.brucielib.test2;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rixonsoft.brucielib.AssetBag;

public class TestAssets extends AssetBag {
    private static final String DEFAULTSKIN = "brucie/ui/cuticle_alphablue.json";


    public Skin defaultSkin;

    @Override
    public void queueAssets() {
        loadAsset(DEFAULTSKIN,Skin.class);
    }

    public void resolveAssets() {
        defaultSkin = assetManager.get(DEFAULTSKIN,Skin.class);
    }
}
