package com.rixonsoft.brucielib.templates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rixonsoft.brucielib.*;
import com.rixonsoft.brucielib.core.scene.BasicScene;

/** Example scene
 *
 */
public class MinimalScene extends BasicScene {

    // Instance for storing asset references.  See inner class definition below.
    private MinimalSceneAssets assets;

    // SpriteBatches are used to manage 2d rendering in LibGDX
    private SpriteBatch batch;

    @Override
    public void draw(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(assets.logo,0,0);
        batch.end();
    }

    @Override
    public void preload() {
        assets = wrangleAssetBag(MinimalSceneAssets.class);
    }

    public void start() {
        batch = wrangle(SpriteBatch.class);
    }


    public static class MinimalSceneAssets extends AssetBag {
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
