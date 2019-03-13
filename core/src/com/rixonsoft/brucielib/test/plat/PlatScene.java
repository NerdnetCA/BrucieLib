package com.rixonsoft.brucielib.test.plat;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.rixonsoft.brucielib.AssetBag;
import com.rixonsoft.brucielib.ecs.FixedRateMaster;
import com.rixonsoft.brucielib.input.DefaultPad;
import com.rixonsoft.brucielib.scene.BasicScene;
import com.rixonsoft.brucielib.test.shooter.components.InputComponent;
import com.rixonsoft.brucielib.test.shooter.components.PlayerComponent;
import com.rixonsoft.brucielib.test.shooter.components.PositionComponent;
import com.rixonsoft.brucielib.test.shooter.components.SpriteComponent;
import com.rixonsoft.brucielib.test.shooter.components.TiledMapComponent;
import com.rixonsoft.brucielib.test.shooter.systems.InputSystem;

public class PlatScene extends BasicScene {
    private MyAssets assets;
    private SpriteBatch batch;

    private PooledEngine engine;
    private PlatMap platMap;
    private PlatRenderManager renderManager;

    @Override
    public void draw(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);
    }

    @Override
    public void preload() {
        assets = wrangleAssetBag(MyAssets.class);
    }

    public void start() {

        batch = new SpriteBatch();
        addDisposable(batch);

        engine = new PooledEngine();
        engine.addSystem(new InputSystem(-10));
        Gdx.input.setInputProcessor(new DefaultPad().setControllable(
                engine.getSystem(InputSystem.class)
        ));

        platMap = new PlatMap();
        platMap.setMap(assets.map_01);

        //FixedRateMaster sys = new FixedRateMaster(0);
        //engine.addSystem(sys);

        renderManager = new PlatRenderManager(1024,768);
        renderManager.setMap(assets.map_01);
        addDisposable(renderManager);
        engine.addSystem(renderManager);




        Entity entity = engine.createEntity();
        addComponent(entity, PlayerComponent.class);
        addComponent(entity, InputComponent.class);
        addComponent(entity,PositionComponent.class);
        addComponent(entity, SpriteComponent.class);



        entity.getComponent(PositionComponent.class).position.x = platMap.player.getRectangle().x;
        entity.getComponent(PositionComponent.class).position.y = platMap.player.getRectangle().y;


        entity.getComponent(SpriteComponent.class).texture  = assets.logo;

        engine.addEntity(entity);


        OrthographicCamera mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(true,1024,768);
        entity = engine.createEntity();
        TiledMapComponent comp = addComponent(entity, TiledMapComponent.class);
        comp.map = assets.map_01;
        comp.renderer = new OrthogonalTiledMapRenderer(assets.map_01,1f);
        comp.renderer.setView(mapCamera);
        engine.addEntity(entity);
    }

    private <T extends Component> T addComponent(Entity e, Class<T> type) {
        e.add(engine.createComponent(type));
        return e.getComponent(type);
    }

    public static class MyAssets extends AssetBag {
        public static final String LOGO = "brucie/logo.png";

        public static final String MAP_1 = "untitled.tmx";

        Texture logo;
        TiledMap map_01;

        @Override
        public void resolveAssets() {
            logo = assetManager.get(LOGO,Texture.class);
            map_01 = assetManager.get(MAP_1,TiledMap.class);
        }

        @Override
        public void queueAssets() {
            loadAsset(LOGO,Texture.class);
            loadAsset(MAP_1,TiledMap.class);
        }
    }

}
