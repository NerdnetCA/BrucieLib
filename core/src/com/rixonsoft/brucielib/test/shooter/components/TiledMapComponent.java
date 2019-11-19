package com.rixonsoft.brucielib.test.shooter.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapComponent implements Component {
    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
}
