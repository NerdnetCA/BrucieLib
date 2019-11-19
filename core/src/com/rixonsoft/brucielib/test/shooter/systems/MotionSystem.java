package com.rixonsoft.brucielib.test.shooter.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.rixonsoft.brucielib.ecs.FramedSystem;
import com.rixonsoft.brucielib.test.shooter.components.*;

public class MotionSystem extends FramedSystem {

    private static final float BRAKE_VALUE = 80f;
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> cm_position = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> cm_velocity = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<ThrustComponent> cm_thrust = ComponentMapper.getFor(ThrustComponent.class);

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(
                        PositionComponent.class,
                        VelocityComponent.class,
                        ThrustComponent.class
                ).get()
        );
    }

    public MotionSystem() {
        super();
    }

    public void frameInterpolate(float delta) {
        // Obtain vector
        Vector2 vec_temp = Pools.obtain(Vector2.class);

        for(Entity e : entities) {
            PositionComponent position = cm_position.get(e);
            VelocityComponent velocity = cm_velocity.get(e);

            vec_temp.set(velocity.vector);
            vec_temp.scl(delta);
            position.interpolation.set(vec_temp);
        }

        Pools.free(vec_temp);
    }

    public void frameStep(float timestep) {
        // Obtain vector
        Vector2 vec_temp = Pools.obtain(Vector2.class);

        for(Entity e : entities) {
            PositionComponent position = cm_position.get(e);
            VelocityComponent velocity = cm_velocity.get(e);
            ThrustComponent thrust = cm_thrust.get(e);

            // Get the thrust vector
            vec_temp.set(thrust.vector);
            if(vec_temp.len() > 0) {
                // Non-zero vector, scale thrust vector by thrust power, by timestep
                vec_temp.scl(thrust.power * timestep);
                // and add it to velocity
                velocity.vector.add(vec_temp);
                // Then copy velocity to check for max bound
                vec_temp.set(velocity.vector);
                if(vec_temp.len() > 120f) {
                    vec_temp.setLength(120f);
                    velocity.vector.set(vec_temp);
                }
            } else {
                // If thrust vector is zero, put on the brakes
                vec_temp.set(velocity.vector);
                float i = vec_temp.len();
                // subtract the braking value, scaled by time
                i -= BRAKE_VALUE*timestep;
                // Check for zero crossing, and set new length
                if(i<0) i=0;
                vec_temp.setLength(i);
                // copy back to velocity
                velocity.vector.set(vec_temp);
            }

            // And finally...
            // Add velocity to position.
            vec_temp.set(velocity.vector);
            vec_temp.scl(timestep);
            position.position.add(vec_temp);
        }

        Pools.free(vec_temp);
    }
}
