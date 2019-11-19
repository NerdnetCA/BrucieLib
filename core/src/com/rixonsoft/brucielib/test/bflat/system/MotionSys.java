package com.rixonsoft.brucielib.test.bflat.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.*;
import com.rixonsoft.brucielib.test.bflat.component.*;

public class MotionSys extends FrameInterpolatedSystem {

    private ImmutableArray<Entity> movable;

    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        movable = engine.getEntitiesFor(
                Family.all(CPosition.class,
                        VelocityComponent.class).get()
        );
    }


    public MotionSys(int priority) {
        super(priority);
    }


    @Override
    public void frameStep(float deltaTime) {

    }

    @Override
    public void frameInterpolate(float deltaTime) {

    }
}
