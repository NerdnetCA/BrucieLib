package com.rixonsoft.brucielib.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.rixonsoft.brucielib.core.ecs.component.Force2Component;
import com.rixonsoft.brucielib.core.ecs.component.Interpolation2Component;
import com.rixonsoft.brucielib.core.ecs.component.Position2Component;
import com.rixonsoft.brucielib.core.ecs.component.Velocity2Component;
import com.rixonsoft.brucielib.ecs.component.*;

public class Mappers {

    public static final ComponentMapper<PlaxLayerComponent> cm_plaxlayer =
            ComponentMapper.getFor(PlaxLayerComponent.class);

    public static final ComponentMapper<InputComponent> cm_input =
            ComponentMapper.getFor(InputComponent.class);

    public static final ComponentMapper<Position2Component> cm_position =
            ComponentMapper.getFor(Position2Component.class);

    public static final ComponentMapper<Interpolation2Component> cm_interpolation =
            ComponentMapper.getFor(Interpolation2Component.class);

    public static final ComponentMapper<Velocity2Component> cm_velocity =
            ComponentMapper.getFor(Velocity2Component.class);

    public static final ComponentMapper<Force2Component> cm_force =
            ComponentMapper.getFor(Force2Component.class);

    public static final ComponentMapper<SpriteComponent> cm_sprite =
            ComponentMapper.getFor(SpriteComponent.class);

    public static final ComponentMapper<TiledMapComponent> cm_map =
            ComponentMapper.getFor(TiledMapComponent.class);

    public static final ComponentMapper<RenderableComponent> cm_renderable =
            ComponentMapper.getFor(RenderableComponent.class);
}
