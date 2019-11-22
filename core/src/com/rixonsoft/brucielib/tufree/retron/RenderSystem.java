package com.rixonsoft.brucielib.tufree.retron;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.rixonsoft.brucielib.tufree.ecs.Mappers;
import com.rixonsoft.brucielib.tufree.ecs.Position2Component;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {


    private final FrameBuffer framebuffer;
    private final int width, height;

    public RenderSystem(int width, int height, int priority) {
        super(
                Family.all(//RenderableComponent.class,
                        Position2Component.class)
                        //.one(SpriteComponent.class, TiledMapComponent.class)
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
        Position2Component position2Component = Mappers.cm_position.get(entity);
    }

    private static class MyComparator implements Comparator<Entity> {

        @Override
        public int compare(Entity entity, Entity t1) {
            //RenderableComponent r1 = Mappers.cm_renderable.get(entity);
            //RenderableComponent r2 = Mappers.cm_renderable.get(t1);

            //return r1.depth - r2.depth;
            return 0;
        }
    }
}
