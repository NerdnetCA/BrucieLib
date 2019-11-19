package com.rixonsoft.brucielib.test.bflat;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.rixonsoft.brucielib.AssetBag;
import com.rixonsoft.brucielib.test.bflat.component.*;
import com.rixonsoft.brucielib.test.bflat.map.MapReader;
import com.rixonsoft.brucielib.test.bflat.system.*;
import com.rixonsoft.brucielib.input.DefaultPad;
import com.rixonsoft.brucielib.retron.DisplayFilter;
import com.rixonsoft.brucielib.core.scene.BasicScene;


public class BFlatGameScene extends BasicScene {

    private PooledEngine engine;
    private MyAssets assets;

    private FrameBuffer framebuffer;

    private int width = 1024, height=768;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private DisplayFilter displayFilter;

    @Override
    public void draw(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        framebuffer.begin();
        engine.update(delta);
        framebuffer.end();

        batch.begin();
        displayFilter.setUniforms();
        batch.draw(framebuffer.getColorBufferTexture(),0,0,width,height);
        batch.end();

    }

    @Override
    public void start() {

        createFramebuffer();

        engine = new PooledEngine();

        createInput();

        createRender();

        createWorld(engine);

    }

    private void createFramebuffer() {
        framebuffer = new FrameBuffer(Pixmap.Format.RGBA8888,width,height,false);
        addDisposable(framebuffer);
        //camera.unproject()
        camera = new OrthographicCamera();
        camera.setToOrtho(true,width,height);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        addDisposable(batch);
        displayFilter = new DisplayFilter();
        addDisposable(displayFilter);
        batch.setShader(displayFilter.getShaderProgram());
    }

    private void createRender() {
        engine.addSystem(new PlaxLayerSys(width,height,5));
        addDisposable(engine.getSystem(PlaxLayerSys.class));
        engine.addSystem(new MapDrawSys(width,height,6));
        addDisposable(engine.getSystem(MapDrawSys.class));
        engine.addSystem(new ActorDrawSys(width,height,10));
        addDisposable(engine.getSystem(ActorDrawSys.class));
    }

    private void createInput() {
        engine.addSystem(new InputSys());
        Gdx.input.setInputProcessor(new DefaultPad().setControllable(
                engine.getSystem(InputSys.class))
        );
        engine.addSystem(new ControlSys());
        Entity e = engine.createEntity();
        CInput input = engine.createComponent(CInput.class);
        e.add(input);
        engine.addEntity(e);
    }


    private void createWorld(PooledEngine eng) {
    /*    Entity e = eng.createEntity();

        e.add(eng.createComponent(CCamera.class));
        e.add(eng.createComponent(CPosition.class));
        CPosition p = e.getComponent(CPosition.class);
        p.position.x = 512f;
        p.position.y = 768f/2f;
        engine.addEntity(e);*/

        MapReader.readMap(engine,assets.map_01);

        Entity e = engine.getEntitiesFor(
                Family.all(CPlayer.class).get()
        ).first();
        e.add(engine.createComponent(CRenderable.class));
        e.add(engine.createComponent(CSprite.class));
        CSprite sc = e.getComponent(CSprite.class);
        sc.texture = assets.boy;
    }

    @Override
    public void preload() {
        assets = wrangleAssetBag(MyAssets.class);
    }

    public static class MyAssets extends AssetBag {
        private static final String LOGO = "brucie/logo.png";
        private static final String SPIDER = "orange-spider.png";
        private static final String BOY = "boy2.png";

        public static final String MAP_1 = "untitled.tmx";

        Texture logo, spider, boy;
        TiledMap map_01;

        @Override
        public void resolveAssets() {
            logo = assetManager.get(LOGO,Texture.class);
            map_01 = assetManager.get(MAP_1,TiledMap.class);
            spider = assetManager.get(SPIDER,Texture.class);
            boy = assetManager.get(BOY,Texture.class);
        }

        @Override
        public void queueAssets() {
            loadAsset(LOGO,Texture.class);
            loadAsset(MAP_1,TiledMap.class);
            loadAsset(SPIDER,Texture.class);
            loadAsset(BOY,Texture.class);
        }
    }

}
