package com.rixonsoft.brucielib.test.shooter.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.rixonsoft.brucielib.test.shooter.components.*;

public class RenderSystem extends EntitySystem {
    private final Batch batch;

    private ComponentMapper<SpriteComponent> cm_sprite = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<PositionComponent> cm_position = ComponentMapper.getFor(PositionComponent.class);

    private ImmutableArray<Entity> entities;

    public RenderSystem(Batch batch) {
        super(10);
        this.batch = batch;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(
                        PositionComponent.class,
                        SpriteComponent.class
                ).get()
        );
    }

    @Override
    public void update(float delta) {
        batch.begin();
        for(Entity e : entities) {
            processEntity(e,delta);
        }
        batch.end();
    }

    protected void processEntity(Entity entity, float deltaTime) {
        SpriteComponent sprite = cm_sprite.get(entity);
        PositionComponent position = cm_position.get(entity);

        this.batch.draw(sprite.texture,position.position.x + position.interpolation.x,
                position.position.y + position.interpolation.y);
    }
}
