package com.rixonsoft.brucielib.tufree.retron.test;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;

public class Test extends IntervalIteratingSystem {


    public Test(Family family, float interval) {
        super(family, interval);
    }

    @Override
    protected void processEntity(Entity entity) {

    }
}
