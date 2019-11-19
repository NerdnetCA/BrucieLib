package com.rixonsoft.brucielib.ecs;

import com.badlogic.ashley.core.EntitySystem;

/**
 * A System that runs at a fixed frame rate.
 *
 * frameStep(stepsize) will be called for every complete frame.
 *
 * frameInterpolate(interpolatedTime) will be called once per pass, after any
 * frameStep calls.
 *
 * (Reviewed Nov 17, 2019 by colin)
 */
public abstract class FramedSystem extends EntitySystem {

    private static final float DEFAULT_TIMESTEP = 1f / 60f;
    private final float timestep;
    private float accumulator;

    public FramedSystem() {
        this(DEFAULT_TIMESTEP);
    }
    public FramedSystem(float timestep) {
        this(timestep,0);
    }
    public FramedSystem(float timestep, int priority) {
        super(priority);
        this.timestep = timestep;
    }

    public void update(float delta) {
        accumulator += delta;
        while (accumulator > timestep) {
            accumulator -= timestep;
            frameStep(timestep);
        }
        frameInterpolate(accumulator);
    }

    public abstract void frameStep(float deltaTime);
    public abstract void frameInterpolate(float deltaTime);
}
