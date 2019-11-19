package com.rixonsoft.brucielib.core.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Total forces applied to Entity motion, in 2d
 *
 */
public class Force2Component implements Component {
    public Vector2 vector = new Vector2();
    public float power =0f;
}
