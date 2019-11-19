package com.rixonsoft.brucielib.test.bflat.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class CPosition implements Component {
    public Vector2 position = new Vector2();
    public Vector2 interpolation = new Vector2();
}
