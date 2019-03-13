package com.rixonsoft.brucielib.test.shooter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    public Vector2 position = new Vector2();
    public Vector2 interpolation = new Vector2();
}
