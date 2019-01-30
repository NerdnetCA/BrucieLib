package com.rixonsoft.brucielib.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rixonsoft.brucielib.BundledAsset;

public enum TestBundle implements BundledAsset {

    DEFAULTSKIN("brucie/ui/cuticle_alphablue.json",Skin.class),
    BRUCIELOGO("brucie/logo.png",Texture.class)
    ;

    private final String path;
    private final Class claz;

    TestBundle(String path, Class clazz) {
        this.path = path;
        this.claz = clazz;
    }

    public String getAssetName() {
        return this.path;
    }

    public Class getAssetClass() {
        return this.claz;
    }

    public Object[] getValues() {
        return TestBundle.values();
    }
}
