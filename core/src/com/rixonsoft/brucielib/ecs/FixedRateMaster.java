package com.rixonsoft.brucielib.ecs;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

public class FixedRateMaster extends EntitySystem {

    private final float timestep;
    protected Array<FramedSystem> subSystems;
    private Comparator<? super EntitySystem> syscomp;
    private float accumulator;

    public FixedRateMaster(int priority) {
        this(priority, 1f/60f);
    }
    public FixedRateMaster(int priority, float framestep) {
        super(priority);
        timestep = framestep;
        subSystems = new Array<FramedSystem>();
        syscomp = new Comparator<EntitySystem>() {
            @Override
            public int compare(EntitySystem entitySystem, EntitySystem t1) {
                return entitySystem.priority - t1.priority;
            }
        };
    }

    public FixedRateMaster addSubSystem(FramedSystem system) {
        system.addedToEngine(getEngine());
        subSystems.add(system);
        subSystems.sort(syscomp);
        return this;
    }

    public void update(float delta) {
        /*for(EntitySystem system : subSystems) {
            system.update(delta);
        }*/
        accumulator += delta;
        while (accumulator > timestep) {
            accumulator -= timestep;
            for(FramedSystem sys : subSystems) {
                sys.frameStep(timestep);
            }
        }
        for(FramedSystem sys : subSystems) {
            sys.frameInterpolate(accumulator);
        }
    }
}
