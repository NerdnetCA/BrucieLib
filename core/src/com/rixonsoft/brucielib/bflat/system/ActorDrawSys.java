package com.rixonsoft.brucielib.bflat.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.rixonsoft.brucielib.bflat.Mappers;
import com.rixonsoft.brucielib.bflat.component.*;

public class ActorDrawSys extends EntitySystem implements Disposable {
    private final Batch batch;
    private final OrthographicCamera camera;

    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> cameras;

    public ActorDrawSys(int width, int height, int priority) {
        super(priority);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,width,height);
        this.batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(CRenderable.class,
                        CPosition.class)
                .one(CSprite.class,
                        CTiledMap.class)
                .get()
        );
        cameras = engine.getEntitiesFor(
                Family.all(CCamera.class,CPosition.class).get()
        );
    }

    @Override
    public void update(float delta) {
        CPosition pos = Mappers.cm_position.get(cameras.first());
        camera.position.x = pos.position.x;
        camera.position.y = pos.position.y;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(Entity e : entities) {
            processEntity(e,delta);
        }
        batch.end();
    }

    protected void processEntity(Entity entity, float deltaTime) {
        CSprite sprite = Mappers.cm_sprite.get(entity);
        CPosition position = Mappers.cm_position.get(entity);

        this.batch.draw(sprite.texture,position.position.x + position.interpolation.x,
                position.position.y + position.interpolation.y);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
