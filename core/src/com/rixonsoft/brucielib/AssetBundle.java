package com.rixonsoft.brucielib;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Iterator;

public abstract class AssetBundle implements Disposable, Wrangled {

    Array<AssetDesc> assetList;
    AssetManager assetManager;
    Array<Object> assetCache;

    public AssetBundle() {
        assetList = new Array<AssetDesc>();
        assetCache = new Array<Object>();
        init();
    }

    public abstract void init();

    public void addAsset(String name, String path, Class assetType) {
        assetList.add(new AssetDesc(name,path,assetType));
    }

    public Object getAsset(int index) {
        return assetCache.get(index);
    }

    public void resolveAssets() {
        Iterator<AssetDesc> iter = assetList.iterator();
        while(iter.hasNext()) {
            AssetDesc d = iter.next();
            assetCache.add(assetManager.get(d.path));
        }

    }

    public void loadAssetsAsync() {
        Iterator<AssetDesc> iter = assetList.iterator();
        while(iter.hasNext()) {
            AssetDesc d = iter.next();
            assetManager.load(d.path, d.assetType);
        }
    }

    public void dispose() {
        Iterator<AssetDesc> iter = assetList.iterator();
        while(iter.hasNext()) {
            assetManager.unload(iter.next().path);
        }
    }

    @Override
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void setGame(BrucieGame game) {

    }

    public class AssetDesc {
        String name;
        String path;
        Class assetType;

        public AssetDesc(String name, String path, Class mytype) {
            this.name = name;
            this.path = path;
            assetType = mytype;
        }

    }
}
