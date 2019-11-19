package com.rixonsoft.brucielib.tufree;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.rixonsoft.brucielib.core.ecs.component.OrthoCameraComponent;

public class TufreeEngine {
    private final PooledEngine engine;

    public TufreeEngine() {
        engine = new PooledEngine();
        Entity cam = engine.createEntity();
        cam.add(engine.createComponent(OrthoCameraComponent.class));
    }

    public void update(float delta) {

    }
}
