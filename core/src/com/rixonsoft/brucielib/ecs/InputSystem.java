package com.rixonsoft.brucielib.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.rixonsoft.brucielib.ecs.component.*;
import com.rixonsoft.brucielib.input.PadControllable;

public class InputSystem extends IteratingSystem implements PadControllable {
    private boolean lefting, righting, upping, downing, shootingA, shootingB, shootingC, pressingEsc;

    public InputSystem() {
        this(0);
    }
    public InputSystem(int priority) {
        super(Family.all(InputComponent.class).get(),priority);
    }

    @Override
    public void reset() {
        lefting = righting = shootingA = shootingB = shootingC = pressingEsc = upping = downing = false;
    }

    @Override
    public void padUp(boolean down) {
        upping = down;
    }

    @Override
    public void padDown(boolean down) {
        downing = down;
    }

    @Override
    public void padLeft(boolean down) {
        lefting = down;
    }

    @Override
    public void padRight(boolean down) {
        righting = down;
    }

    @Override
    public void buttonA(boolean down) {
        shootingA = down;
    }

    @Override
    public void buttonB(boolean down) { shootingB = down;
    }

    @Override
    public void buttonC(boolean down) { shootingC = down;
    }

    @Override
    public void buttonEsc(boolean down) {
        pressingEsc = down;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InputComponent input = Mappers.cm_input.get(entity);

        input.axisY = upping ? 1 : downing ? -1 : 0;
        input.axisX = righting ? 1 : lefting ? -1 : 0;
        input.buttonA = shootingA;
        input.buttonB = shootingB;
        input.buttonC = shootingC;
        input.buttonStart = pressingEsc;
    }
}
