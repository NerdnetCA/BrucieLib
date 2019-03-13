package com.rixonsoft.brucielib.bflat.system;

import com.badlogic.ashley.core.EntitySystem;

public abstract class FrameInterpolatedSystem extends EntitySystem {

    private static final float DEFAULT_TIMESTEP = 1f / 60f;
    private final float timestep;
    private float accumulator;

    public FrameInterpolatedSystem() {
        this(DEFAULT_TIMESTEP);
    }
    public FrameInterpolatedSystem(float timestep) {
        this(timestep,0);
    }
    public FrameInterpolatedSystem(float timestep, int priority) {
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
