package com.rixonsoft.brucielib.test.bflat.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.rixonsoft.brucielib.test.bflat.Mappers;
import com.rixonsoft.brucielib.test.bflat.component.CCamera;
import com.rixonsoft.brucielib.test.bflat.component.CPosition;
import com.rixonsoft.brucielib.test.bflat.component.CRenderable;
import com.rixonsoft.brucielib.test.bflat.component.CTiledMap;

public class MapDrawSys extends EntitySystem implements Disposable {
    private final OrthographicCamera camera;

    private ImmutableArray<Entity> tiledmaps;
    private ImmutableArray<Entity> cameras;
    private float globalX, globalY;

    public MapDrawSys(int width, int height, int priority) {
        super(priority);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,width,height);
    }

    public void addedToEngine(Engine engine) {
        tiledmaps = engine.getEntitiesFor(
                Family.all(CRenderable.class,
                        CTiledMap.class)
                        .get()
        );
        cameras = engine.getEntitiesFor(
                Family.all(CCamera.class,
                        CPosition.class
                )
                        .get()
        );
    }

    @Override
    public void update(float delta) {
        for(Entity e : cameras) {
            CPosition pos = Mappers.cm_position.get(e);
            globalX = pos.position.x + pos.interpolation.x;
            globalY = pos.position.y + pos.interpolation.y;
        }
        for(Entity e : tiledmaps) {
            processEntity(e,delta);
        }
    }

    protected void processEntity(Entity entity, float deltaTime) {
        CTiledMap map = Mappers.cm_map.get(entity);


        camera.position.x = globalX;
        camera.position.y = globalY;
        camera.update();

        map.renderer.setView(camera);
        map.renderer.render();
    }

    @Override
    public void dispose() {

    }
}
