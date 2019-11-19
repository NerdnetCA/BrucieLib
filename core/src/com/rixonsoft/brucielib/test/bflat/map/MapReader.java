package com.rixonsoft.brucielib.test.bflat.map;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.rixonsoft.brucielib.test.bflat.component.*;

public class MapReader {

    public static void readMap(PooledEngine engine, TiledMap map) {
        readPlax(engine, map);
        readTiles(engine, map);
        readCamera(engine, map);
        readPlayer(engine, map);
    }

    private static void readCamera(PooledEngine engine, TiledMap map) {
        Entity e = engine.createEntity();

        e.add(engine.createComponent(CCamera.class));
        e.add(engine.createComponent(CPosition.class));
        CPosition p = e.getComponent(CPosition.class);
        p.position.x = 512f;
        p.position.y = 768f/2f;
        Rectangle r =
                ((RectangleMapObject)map.getLayers().get("obj").getObjects().get("camera")).getRectangle();
        p.position.x = r.x;
        p.position.y = r.y;
        engine.addEntity(e);
    }

    private static void readPlayer(PooledEngine engine, TiledMap map) {
        Entity e = engine.createEntity();

        e.add(engine.createComponent(CPlayer.class));
        e.add(engine.createComponent(CPosition.class));
        CPosition p = e.getComponent(CPosition.class);
        Rectangle r =
                ((RectangleMapObject)map.getLayers().get("obj").getObjects().get("player")).getRectangle();
        p.position.x = r.x;
        p.position.y = r.y;
        engine.addEntity(e);
    }

    private static void readPlax(PooledEngine engine, TiledMap map) {
        for(MapLayer layer : map.getLayers()) {
            if(layer instanceof TiledMapImageLayer) {
                TiledMapImageLayer tim = (TiledMapImageLayer)layer;
                MapProperties props = tim.getProperties();

                if(props.containsKey("PLAX")) {
                    int width, height, order;
                    float factorX, factorY;

                    TextureRegion region = tim.getTextureRegion();

                    order = props.get("order", 0, Integer.class);
                    width = props.get("width", region.getRegionWidth(), Integer.class);
                    height = props.get("height", region.getRegionHeight(), Integer.class);
                    factorX = props.get("scrollFactorX", 1f, Float.class);
                    factorY = props.get("scrollFactorY", 1f, Float.class);

                    createPlaxLayer(engine, region,
                            width, height,
                            factorX, factorY,
                            order);
                }
            }
        }
    }

    private static void createPlaxLayer(PooledEngine engine, TextureRegion region, int width, int height,
                                        float xFactor, float yFactor, int order) {
        Entity e = engine.createEntity();
        e.add(engine.createComponent(CRenderable.class));
        e.add(engine.createComponent(CPlax.class));
        CPlax c = e.getComponent(CPlax.class);
        c.texture = region;
        c.width = width;
        c.height = height;
        c.xFactor = xFactor;
        c.yFactor = yFactor;
        c.order = order;
        engine.addEntity(e);

    }

    private static void readTiles(PooledEngine engine, TiledMap map) {
        for(MapLayer layer : map.getLayers()) {
            if(layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tim = (TiledMapTileLayer)layer;
                MapProperties props = tim.getProperties();

                createTileLayer(engine, map);

            }
        }
    }

    private static void createTileLayer(PooledEngine engine, TiledMap map) {
        Entity e = engine.createEntity();
        e.add(engine.createComponent(CRenderable.class));
        e.add(engine.createComponent(CTiledMap.class));
        CTiledMap c = e.getComponent(CTiledMap.class);
        c.map = map;
        c.renderer = new OrthogonalTiledMapRenderer(map,1f);
        engine.addEntity(e);

    }

}
