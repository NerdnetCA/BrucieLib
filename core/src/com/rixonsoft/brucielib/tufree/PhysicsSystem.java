package com.rixonsoft.brucielib.tufree;


import com.rixonsoft.brucielib.ecs.FramedSystem;

/**
 * Physics System for the TuFree example/template.
 *
 * The PhysicsSystem will be responsible for updating all
 * position and status data for all Entities, accounting
 * for collisions.
 *
 */
public class PhysicsSystem extends FramedSystem {

    /**
     * Perform one physics step. The parameter should always be
     * the same value as the timestep the system was created with.
     *
     * @param deltaTime framestep size
     */
    @Override
    public void frameStep(float deltaTime) {

    }

    /**
     * Update interpolated values for all Entities.
     *
     * This calculates a position offset for Entities, for the given
     * partial timestep. This is not to be calculated into the physics,
     * but to provide accurate rendering information for frames rendered
     * in-between physics ticks.
     *
     * @param deltaTime time since last physics tick. (NOT since last rendered frame!)
     */
    @Override
    public void frameInterpolate(float deltaTime) {

    }
}
