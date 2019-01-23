package com.rixonsoft.brucielib;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Iterator;


public class Wrangler implements Disposable {

    private AssetManager assetManager;
    private BrucieGame brucieGame;

    private Array<Disposable> disposables = new Array<Disposable>();

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    public void setGame(BrucieGame game) { this.brucieGame = game; }

    public <T> T wrangle(Class<T> type) {
        try {
            T o = type.newInstance();
            if(o instanceof Wrangled) {
                initialize((Wrangled) o);
            }
            return o;
        } catch (IllegalAccessException e) {

        } catch (InstantiationException e) {

        }
        return null;
    }

    public void initialize(Wrangled o) {
        o.setAssetManager(assetManager);
        o.setGame(brucieGame);
        if (o instanceof Disposable) {
            disposables.add((Disposable)o);
        }
    }

    public void dispose() {
        Iterator<Disposable> iter = disposables.iterator();
        while(iter.hasNext()) {
            iter.next().dispose();
        }
    }
}
