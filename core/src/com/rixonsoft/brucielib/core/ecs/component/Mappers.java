package com.rixonsoft.brucielib.core.ecs.component;

import com.badlogic.ashley.core.ComponentMapper;
import com.rixonsoft.brucielib.tufree.ecs.Position2Component;
import com.rixonsoft.brucielib.tufree.ecs.Velocity2Component;

public class Mappers {

    public static final ComponentMapper<InputComponent> cm_input =
            ComponentMapper.getFor(InputComponent.class);

    public static final ComponentMapper<Position2Component> cm_position =
            ComponentMapper.getFor(Position2Component.class);

    public static final ComponentMapper<Velocity2Component> cm_velocity =
            ComponentMapper.getFor(Velocity2Component.class);

    public static final ComponentMapper<Force2Component> cm_force =
            ComponentMapper.getFor(Force2Component.class);
}
