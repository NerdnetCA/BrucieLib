package com.rixonsoft.brucielib.ecs;

import com.badlogic.ashley.core.*;
import com.rixonsoft.brucielib.core.ecs.component.OrthoCameraComponent;

public class RetronEngine {

    private final PooledEngine engine;

    public RetronEngine() {

        engine = new PooledEngine();

        addCamera();
    }

    public void update(float delta) {

    }

    public void addCamera() {
        Entity cam = engine.createEntity();
        cam.add(engine.createComponent(OrthoCameraComponent.class));


    }

}
