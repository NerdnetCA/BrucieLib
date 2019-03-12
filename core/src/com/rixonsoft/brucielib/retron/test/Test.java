package com.rixonsoft.brucielib.retron.test;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.systems.IntervalSystem;

public class Test extends IntervalIteratingSystem {


    public Test(Family family, float interval) {
        super(family, interval);
    }

    @Override
    protected void processEntity(Entity entity) {

    }
}
