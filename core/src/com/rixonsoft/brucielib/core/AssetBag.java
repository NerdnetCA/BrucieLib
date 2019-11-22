package com.rixonsoft.brucielib.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.rixonsoft.brucielib.core.scene.BrucieGlobal;

import java.util.Iterator;

/**
 * Base class for bundles of assets.
 *
 * See MinimalScene for example of how to subclass.
 *
 * By implementing BrucieGlobal and Disposable, we tell the wrangler
 * that the instance needs a reference to BrucieGame, and that
 * it needs to be dispose()'d
 *
 */
public abstract class AssetBag implements BrucieGlobal, Disposable {
    private static final String TAG = "ASSETBAG";

    protected AssetManager assetManager;

    private Array<String> assetList;

    public AssetBag() {
        assetList = new Array<String>();
    }

    public void setGame(BrucieGame game) {
        // When the wrangler creates this object, it gives us
        // a reference to the BrucieGame, so we can grab the AssetManager.
        assetManager = game.assetManager;
        // And call queueAssets();
        queueAssets();
    }

    // See MinimalScene
    public abstract void resolveAssets();
    public abstract void queueAssets();


    /** Scenes' custom AssetBags should use this method to queue asset loads.
     *
     * @param name
     * @param assetType
     */
    public void loadAsset(String name, Class assetType) {
        assetManager.load(name, assetType);
        assetList.add(name);
    }

    /** loadAsset, but with parameter.
     *
     * @param name
     * @param assetType
     * @param param
     */
    public void loadAsset(String name, Class assetType, AssetLoaderParameters param) {
        assetManager.load(name, assetType, param);
        assetList.add(name);
    }

    /**
     * We need to unload our assets when no longer needed. The wrangler should
     * call our dispose method.
     */
    @Override
    public void dispose() {
        Gdx.app.log(TAG, "Disposing AssetBag " + getClass().getName());
        Iterator<String> iter = assetList.iterator();
        while(iter.hasNext()) {
            assetManager.unload(iter.next());
        }
    }
}
