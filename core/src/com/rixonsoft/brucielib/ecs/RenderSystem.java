package com.rixonsoft.brucielib.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.rixonsoft.brucielib.ecs.component.InterpolationComponent;
import com.rixonsoft.brucielib.ecs.component.PositionComponent;
import com.rixonsoft.brucielib.ecs.component.RenderableComponent;
import com.rixonsoft.brucielib.ecs.component.SpriteComponent;
import com.rixonsoft.brucielib.ecs.component.TiledMapComponent;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {


    private final FrameBuffer framebuffer;
    private final int width, height;

    public RenderSystem(int width, int height, int priority) {
        super(
                Family.all(RenderableComponent.class, PositionComponent.class)
                        .one(SpriteComponent.class, TiledMapComponent.class)
                        .get(),
                new MyComparator(),
                priority);

        framebuffer = new FrameBuffer(Pixmap.Format.RGBA8888,width,height,false);
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(float delta) {
        framebuffer.begin();
        super.update(delta);
        framebuffer.end();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpriteComponent spriteComponent = Mappers.cm_sprite.get(entity);
        PositionComponent positionComponent = Mappers.cm_position.get(entity);
        InterpolationComponent interpolationComponent = Mappers.cm_interpolation.get(entity);
        TiledMapComponent mapComponent = Mappers.cm_map.get(entity);

        if(spriteComponent != null) {

        } else if(mapComponent != null) {
            mapComponent.renderer.render();
        }
    }

    private static class MyComparator implements Comparator<Entity> {

        @Override
        public int compare(Entity entity, Entity t1) {
            RenderableComponent r1 = Mappers.cm_renderable.get(entity);
            RenderableComponent r2 = Mappers.cm_renderable.get(t1);

            return r1.depth - r2.depth;
        }
    }
}
