package com.rixonsoft.brucielib.core.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/** Position in 2d space.
 *
 */
public class Position2Component implements Component {
    public Vector2 position = new Vector2();
}
