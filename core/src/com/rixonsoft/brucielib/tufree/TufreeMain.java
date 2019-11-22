package com.rixonsoft.brucielib.tufree;

import com.badlogic.gdx.graphics.Texture;
import com.rixonsoft.brucielib.core.AssetBag;
import com.rixonsoft.brucielib.core.scene.BasicScene;

public class TufreeMain extends BasicScene {

    // Instance for storing asset references.  See inner class definition below.
    private TufreeMainAssets assets;

    private TufreeEngine engine;

    @Override
    public void draw(float delta) {
        engine = new TufreeEngine();

    }

    @Override
    public void preload() {
        assets = wrangleAssetBag(TufreeMainAssets.class);
    }

    public void start() {
        assets.resolveAssets();
    }


    public static class TufreeMainAssets extends AssetBag {
        public static final String LOGO = "brucie/logo.png";

        Texture logo;

        @Override
        public void resolveAssets() {
            logo = assetManager.get(LOGO,Texture.class);
        }

        @Override
        public void queueAssets() {
            loadAsset(LOGO,Texture.class);
        }
    }
}
