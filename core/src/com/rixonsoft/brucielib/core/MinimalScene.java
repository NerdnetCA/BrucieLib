package com.rixonsoft.brucielib.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rixonsoft.brucielib.core.AssetBag;
import com.rixonsoft.brucielib.core.scene.BasicScene;

/** Example scene.
 *
 *
 *
 */
public class MinimalScene extends BasicScene {

    // Instance for storing asset references.  See inner class definition below.
    private MinimalSceneAssets assets;

    // SpriteBatches are used to manage 2d rendering in LibGDX
    private SpriteBatch batch;

    /**
     * This is the core of the scene, which is called every frame.
     *
     * This shows a basic do-nothing LibGDX example.
     *
     * @param delta time since last frame.
     */
    @Override
    public void draw(float delta) {
        // Clear the screen. Set the clear color (rgba)
        Gdx.gl20.glClearColor(0,0,0,1);
        // Clear the "color buffer" (yes, there are other buffers)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin the SpriteBatch - batches provide a context within which
        // to render.
        batch.begin();

        // Draw the logo at 0,0
        batch.draw(assets.logo,0,0);

        // Finalize the batch.
        batch.end();
    }

    /**
     * preload is called by BrucieGame to cause the scene's assets
     * to begin loading.
     *
     * Normally, it is enough to call wrangleAssetBag() with your inline subclass
     * of AssetBag, and store the returned instance.
     */
    @Override
    public void preload() {
        // TODO: more documentation on the "wrangler" concept
        // This call is provided by the parent class, and creates
        // an instance of the given AssetBag class, with all the
        // assets queued for loading.
        assets = wrangleAssetBag(MinimalSceneAssets.class);
    }

    /** The BrucieGame system calls start() when it is ready to start
     * rendering the scene. The assets will be loaded, but you
     * must first call resolveAssets() to obtain references to each asset.
     *
     */
    public void start() {
        assets.resolveAssets();

        // Create a LibGDX SpriteBatch. The wrangler concept is useful in this case
        // for automating garbage collection. If you use the wrangler to create the batch,
        // you don't have to worry about dispose()'ing it later.
        batch = wrangle(SpriteBatch.class);
    }


    /** A bundle of assets. Subclasses should follow this as a template.
     *
     */
    public static class MinimalSceneAssets extends AssetBag {
        // Internal path of the asset file. (in the LibGDX assets folder)
        public static final String LOGO = "brucie/logo.png";

        // A field to hold a ref to each asset.
        Texture logo;

        /**
         * The system calls this when you need to fill in the asset fields.
         */
        @Override
        public void resolveAssets() {
            // The AssetManager is provided by parent class - just get() each
            // asset and store the reference.
            logo = assetManager.get( LOGO, Texture.class);
        }

        /**
         * The system calls this when you need to start loading the scene's assets.
         */
        @Override
        public void queueAssets() {
            // Use loadAsset() provided by the parent, instead of using LibGDX AssetManager
            // directly. This gives you better garbage collection.

            // Load the file as an instance of given class
            loadAsset( LOGO, Texture.class );
        }
    }
}
