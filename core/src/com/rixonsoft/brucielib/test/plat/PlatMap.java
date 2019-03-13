package com.rixonsoft.brucielib.test.plat;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;

public class PlatMap {

    public TextureRegion background;

    public MapLayers layers;
    public TiledMapImageLayer bglayer;
    public RectangleMapObject player;

    public void setMap(TiledMap map) {
        if(map != null) {
            // ?
        }

        layers = map.getLayers();
        bglayer = (TiledMapImageLayer) layers.get("bg");
        background = bglayer.getTextureRegion();

        MapObject obj = layers.get("obj").getObjects().get("logo");
        player = (RectangleMapObject)obj;
    }

}
