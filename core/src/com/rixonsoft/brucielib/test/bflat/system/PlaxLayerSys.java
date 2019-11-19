package com.rixonsoft.brucielib.test.bflat.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.rixonsoft.brucielib.test.bflat.Mappers;
import com.rixonsoft.brucielib.test.bflat.component.*;


import java.util.Comparator;


public class PlaxLayerSys extends SortedIteratingSystem implements Disposable {
    private final Batch batch;
    private final OrthographicCamera camera;
    private final int width, height;
    private ImmutableArray<Entity> cameras;
    private float globalX, globalY;

    public PlaxLayerSys(int width, int height, int priority) {
        super(
                Family.all(CRenderable.class,
                        CPlax.class)
                        .get(),
                new MyComparator(),
        priority);

        this.width = width;
        this.height = height;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,width,height);
        this.batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        cameras = engine.getEntitiesFor(
                Family.all(CCamera.class,
                        CPosition.class
                        )
                        .get()
        );
    }

    @Override
    public void update(float delta) {
        batch.begin();
        for(Entity e : cameras) {
            CPosition pos = Mappers.cm_position.get(e);
            globalX = pos.position.x + pos.interpolation.x;
            globalY = pos.position.y + pos.interpolation.y;
        }
        super.update(delta);
        batch.end();
    }

    protected void processEntity(Entity entity, float deltaTime) {
        CPlax plax = Mappers.cm_plax.get(entity);

        float posx = -(globalX) * plax.xFactor;
        float posy = -(globalY) * plax.yFactor;

        while(posx > 0) {
            posx -= plax.width;
        }
        while(posy > 0) {
            posy -= plax.height;
        }

        float curx = posx;
        while(posy < height) {
            this.batch.draw(plax.texture,
                    curx,
                    posy,
                    plax.width, plax.height
            );
            curx += plax.width;
            if(curx > width) {
                curx = posx;
                posy += plax.height;
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


    private static class MyComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity entity, Entity t1) {
            CPlax r1 = Mappers.cm_plax.get(entity);
            CPlax r2 = Mappers.cm_plax.get(t1);

            return r1.order - r2.order;
        }
    }
}
