package com.rixonsoft.brucielib.bflat.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CPlax implements Component {
    public TextureRegion texture;
    public float width, height;
    public float xFactor, yFactor;
    public int order;
}
