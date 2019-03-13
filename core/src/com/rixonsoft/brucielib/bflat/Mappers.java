package com.rixonsoft.brucielib.bflat;

import com.badlogic.ashley.core.ComponentMapper;
import com.rixonsoft.brucielib.bflat.component.*;

public class Mappers {

    public static final ComponentMapper<CInput> cm_input =
            ComponentMapper.getFor(CInput.class);

    public static final ComponentMapper<CPosition> cm_position =
            ComponentMapper.getFor(CPosition.class);

    public static final ComponentMapper<VelocityComponent> cm_velocity =
            ComponentMapper.getFor(VelocityComponent.class);

    public static final ComponentMapper<ThrustComponent> cm_force =
            ComponentMapper.getFor(ThrustComponent.class);

    public static final ComponentMapper<CSprite> cm_sprite =
            ComponentMapper.getFor(CSprite.class);

    public static final ComponentMapper<CTiledMap> cm_map =
            ComponentMapper.getFor(CTiledMap.class);

    public static final ComponentMapper<CRenderable> cm_renderable =
            ComponentMapper.getFor(CRenderable.class);

    public static final ComponentMapper<CPlax> cm_plax =
            ComponentMapper.getFor(CPlax.class);

    public static final ComponentMapper<CControl> cm_control =
            ComponentMapper.getFor(CControl.class);
}
