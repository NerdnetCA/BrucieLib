package com.rixonsoft.brucielib.test.shooter.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.rixonsoft.brucielib.test.shooter.components.InputComponent;
import com.rixonsoft.brucielib.test.shooter.components.PlayerComponent;
import com.rixonsoft.brucielib.test.shooter.components.PositionComponent;
import com.rixonsoft.brucielib.test.shooter.components.ThrustComponent;
import com.rixonsoft.brucielib.test.shooter.components.VelocityComponent;

public class LogicSystem extends IteratingSystem {

    private ComponentMapper<VelocityComponent> cm_velocity = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<ThrustComponent> cm_thrust = ComponentMapper.getFor(ThrustComponent.class);
    private ComponentMapper<InputComponent> cm_input = ComponentMapper.getFor(InputComponent.class);

    public LogicSystem(int priority) {
        super(
                Family.all(PlayerComponent.class,
                        VelocityComponent.class,
                        ThrustComponent.class,
                        InputComponent.class).get(),
        priority);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ThrustComponent thrust = cm_thrust.get(entity);
        InputComponent input = cm_input.get(entity);

        thrust.vector.set(input.axisX,0);
        //thrust.power = 30f;



    }

}
