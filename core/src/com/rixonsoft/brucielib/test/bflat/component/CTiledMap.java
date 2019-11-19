package com.rixonsoft.brucielib.test.bflat.component;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class CTiledMap implements Component {
    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
}
