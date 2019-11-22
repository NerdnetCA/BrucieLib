package com.rixonsoft.brucielib.test.plat;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.rixonsoft.brucielib.tufree.retron.DisplayFilter;
import com.rixonsoft.brucielib.test.shooter.components.PositionComponent;
import com.rixonsoft.brucielib.test.shooter.components.SpriteComponent;
import com.rixonsoft.brucielib.test.shooter.components.TiledMapComponent;

public class PlatRenderManager extends EntitySystem implements Disposable {
    private final Batch batch;
    private final FrameBuffer framebuffer;
    private final OrthographicCamera bgCamera;
    private final OrthographicCamera mapCamera;
    private final DisplayFilter displayFilter;
    private final ShaderProgram defaultShader;
    private final OrthographicCamera finalCamera;
    private final int width, height;

    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    private MapLayers layers;
    private TiledMapImageLayer bglayer;

    private ComponentMapper<SpriteComponent> cm_sprite = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<PositionComponent> cm_position = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<TiledMapComponent> cm_maps = ComponentMapper.getFor(TiledMapComponent.class);

    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> maps;
    private ImmutableArray<Entity> plaxlayers;


    public PlatRenderManager(int width, int height) {
        super(10);

        this.width = width;
        this.height = height;

        framebuffer = new FrameBuffer(Pixmap.Format.RGBA8888,width,height,false);

        bgCamera = new OrthographicCamera();
        bgCamera.setToOrtho(true,width,height);
        mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(true,width,height);
        finalCamera = new OrthographicCamera();
        finalCamera.setToOrtho(false,width,height);

        batch = new SpriteBatch();


        // OpenGL shader for filtering the framebuffer when rendering to display
        displayFilter = new DisplayFilter();

        // Set default filter parameters
        displayFilter.setBrite(1.0f);
        displayFilter.setSaturation(1.2f);

        defaultShader = batch.getShader();
    }

    public void setMap(TiledMap map) {
        mapRenderer = new OrthogonalTiledMapRenderer(map,1f);
        mapRenderer.setView(mapCamera);
        this.map = map;
        layers = map.getLayers();
        bglayer = (TiledMapImageLayer)layers.get("bg");
        MapObject o = layers.get("obj").getObjects().get(0);
        RectangleMapObject ro = (RectangleMapObject)o;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(
                        PositionComponent.class,
                        SpriteComponent.class
                ).get()
        );
        maps = engine.getEntitiesFor(
                Family.all(TiledMapComponent.class).get()
        );
        plaxlayers = engine.getEntitiesFor(
                Family.all(TiledMapComponent.class).get()
        );
    }

    @Override
    public void update(float delta) {

        framebuffer.begin();

        batch.setProjectionMatrix(bgCamera.combined);
        batch.setShader(defaultShader);
        batch.begin();
        batch.draw(bglayer.getTextureRegion(),0,0,width,height);
        batch.end();

        // bgrender
        //mapRenderer.render();
        for(Entity e : maps) {
            TiledMapComponent map = cm_maps.get(e);

            map.renderer.render();
        }

        batch.setProjectionMatrix(mapCamera.combined);
        batch.begin();

        for(Entity e : entities) {
            processEntity(e,delta);
        }
        batch.end();
        framebuffer.end();

        batch.setProjectionMatrix(finalCamera.combined);
        batch.setShader(displayFilter.getShaderProgram());
        batch.begin();
        displayFilter.setUniforms();
        batch.draw(framebuffer.getColorBufferTexture(),0,0);
        batch.end();
    }

    protected void processEntity(Entity entity, float deltaTime) {
        SpriteComponent sprite = cm_sprite.get(entity);
        PositionComponent position = cm_position.get(entity);

        this.batch.draw(sprite.texture,position.position.x + position.interpolation.x,
                position.position.y + position.interpolation.y);
    }

    public void dispose() {
        batch.dispose();
        displayFilter.dispose();
        framebuffer.dispose();

    }
}
