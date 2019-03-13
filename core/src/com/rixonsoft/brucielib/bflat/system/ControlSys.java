package com.rixonsoft.brucielib.bflat.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.rixonsoft.brucielib.bflat.Mappers;
import com.rixonsoft.brucielib.bflat.component.*;

public class ControlSys extends EntitySystem {

    private ImmutableArray<Entity> inputs;
    private ImmutableArray<Entity> players;

    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        inputs = engine.getEntitiesFor(
                Family.all(CInput.class).get()
        );
        players = engine.getEntitiesFor(
                Family.all(CPlayer.class,
                        CPosition.class).get()
        );
    }

    @Override
    public void update(float delta) {
        Entity ply = players.first();
        Entity inp = inputs.first();

        CPosition posply = Mappers.cm_position.get(ply);

        float globalX = posply.position.x;
        float globalY = posply.position.y;

        CInput input = Mappers.cm_input.get(inp);

        globalX += input.axisX * 120f * delta;
        globalY += input.axisY * 120f * delta;

        posply.position.x = globalX;
        posply.position.y = globalY;

    }
}
