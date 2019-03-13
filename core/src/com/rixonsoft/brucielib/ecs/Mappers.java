package com.rixonsoft.brucielib.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.rixonsoft.brucielib.ecs.component.*;

public class Mappers {

    public static final ComponentMapper<PlaxLayerComponent> cm_plaxlayer =
            ComponentMapper.getFor(PlaxLayerComponent.class);

    public static final ComponentMapper<InputComponent> cm_input =
            ComponentMapper.getFor(InputComponent.class);

    public static final ComponentMapper<PositionComponent> cm_position =
            ComponentMapper.getFor(PositionComponent.class);

    public static final ComponentMapper<InterpolationComponent> cm_interpolation =
            ComponentMapper.getFor(InterpolationComponent.class);

    public static final ComponentMapper<VelocityComponent> cm_velocity =
            ComponentMapper.getFor(VelocityComponent.class);

    public static final ComponentMapper<ForceComponent> cm_force =
            ComponentMapper.getFor(ForceComponent.class);

    public static final ComponentMapper<SpriteComponent> cm_sprite =
            ComponentMapper.getFor(SpriteComponent.class);

    public static final ComponentMapper<TiledMapComponent> cm_map =
            ComponentMapper.getFor(TiledMapComponent.class);

    public static final ComponentMapper<RenderableComponent> cm_renderable =
            ComponentMapper.getFor(RenderableComponent.class);
}
