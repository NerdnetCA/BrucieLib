package com.rixonsoft.brucielib.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ForceComponent implements Component {
    public Vector2 vector = new Vector2();
    public float power =0f;
}
