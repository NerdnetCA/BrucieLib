package com.rixonsoft.brucielib.test.shooter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ThrustComponent implements Component {
    public Vector2 vector = new Vector2();
    public float power =0f;
}
