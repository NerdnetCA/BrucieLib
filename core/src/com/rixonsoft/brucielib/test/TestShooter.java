package com.rixonsoft.brucielib.test;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.rixonsoft.brucielib.AssetBag;
import com.rixonsoft.brucielib.retron.RetronBatch;
import com.rixonsoft.brucielib.core.scene.BasicScene;
import com.rixonsoft.brucielib.input.DefaultPad;
import com.rixonsoft.brucielib.test.shooter.components.InputComponent;
import com.rixonsoft.brucielib.test.shooter.components.PlayerComponent;
import com.rixonsoft.brucielib.test.shooter.components.PositionComponent;
import com.rixonsoft.brucielib.test.shooter.components.SpriteComponent;
import com.rixonsoft.brucielib.test.shooter.components.ThrustComponent;
import com.rixonsoft.brucielib.test.shooter.components.VelocityComponent;
import com.rixonsoft.brucielib.test.shooter.systems.InputSystem;
import com.rixonsoft.brucielib.test.shooter.systems.LogicSystem;
import com.rixonsoft.brucielib.test.shooter.systems.MotionSystem;
import com.rixonsoft.brucielib.test.shooter.systems.RenderSystem;

public class TestShooter extends BasicScene {
    private MyAssets assets;
    private RetronBatch batch;

    private PooledEngine engine;
    private Entity player;
    private Entity player2;

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
        batch = new RetronBatch(800, 600);
        addDisposable(batch);

        engine = new PooledEngine();

        engine.addSystem(new InputSystem(-10));
        engine.addSystem(new LogicSystem(-5));
        engine.addSystem(new MotionSystem());
        engine.addSystem(new RenderSystem(batch));


        Gdx.input.setInputProcessor(new DefaultPad().setControllable(
                engine.getSystem(InputSystem.class)
        ));


        player = engine.createEntity();

        engine.addEntity(player);

        addComponent(player,PlayerComponent.class);
        addComponent(player,InputComponent.class);
        addComponent(player,PositionComponent.class);
        addComponent(player,VelocityComponent.class);
        addComponent(player,ThrustComponent.class);
        addComponent(player,SpriteComponent.class);

        player.getComponent(PositionComponent.class).position.x = 200f;
        player.getComponent(PositionComponent.class).position.y = 80f;
        player.getComponent(ThrustComponent.class).power = 80f;
        player.getComponent(SpriteComponent.class).texture = assets.logo;

    }

    private void addComponent(Entity e, Class type) {
        e.add(engine.createComponent(type));
    }

    public static class MyAssets extends AssetBag {
        public static final String LOGO = "brucie/logo.png";

        Texture logo;

        @Override
        public void resolveAssets() {
            logo = assetManager.get(LOGO,Texture.class);
        }

        @Override
        public void queueAssets() {
            loadAsset(LOGO,Texture.class);
        }
    }
}

