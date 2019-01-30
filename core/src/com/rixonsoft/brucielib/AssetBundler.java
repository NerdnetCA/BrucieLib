package com.rixonsoft.brucielib;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

public class AssetBundler<T extends BundledAsset> implements Disposable, Wrangled {

    private AssetManager assetManager;
    private BrucieGame brucieGame;
    private BundledAsset myBundle;

    public void loadBundle(BundledAsset bundle) {
        myBundle = bundle;
        for (Object o : bundle.getValues()) {
            T obj = (T)o;
            assetManager.load(obj.getAssetName(),obj.getAssetClass());
        }
    }

    public Object getAsset(BundledAsset a) {
        return assetManager.get(a.getAssetName(),a.getAssetClass());
    }

    public <T> T getAsset(BundledAsset a, Class<T> type) {
        return assetManager.get(a.getAssetName(),type);
    }

    @Override
    public void dispose() {
        for (Object o : myBundle.getValues()) {
            T obj = (T)o;
            assetManager.unload(obj.getAssetName());
        }
    }

    @Override
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void setGame(BrucieGame game) {
        this.brucieGame = game;
    }
}
