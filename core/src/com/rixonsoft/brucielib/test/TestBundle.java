package com.rixonsoft.brucielib.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rixonsoft.brucielib.core.AssetBag;

/** Example AssetBag.
 *
 * 1. Create constant fields containing asset path names.
 *
 * 2. Create fields to hold instance references to your assets.
 *
 * 3. Implement resolveAssets() to fill in the instance fields.
 *
 * 4. Implement queueAssets() to load your assets via AssetManager.
 *
 */
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
