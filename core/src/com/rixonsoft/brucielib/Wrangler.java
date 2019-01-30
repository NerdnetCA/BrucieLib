package com.rixonsoft.brucielib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;


public class Wrangler implements Disposable {
    private static final String TAG = "WRANGLER";

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
            } else if(o instanceof  Disposable) {
                disposables.add((Disposable)o);
            }
            return o;
        } catch (IllegalAccessException e) {
            Gdx.app.log(TAG,"IllegalAccessException wrangling "+type.getCanonicalName());
        } catch (InstantiationException e) {
            Gdx.app.log(TAG,"InstantiationException wrangling "+type.getCanonicalName());
        }
        return null;
    }

    private void initialize(Wrangled o) {
        o.setAssetManager(assetManager);
        o.setGame(brucieGame);
        if (o instanceof Disposable) {
            disposables.add((Disposable)o);
        }
    }

    public void dispose() {
        for (Disposable d:disposables
             ) {
            d.dispose();
        }
    }
}
