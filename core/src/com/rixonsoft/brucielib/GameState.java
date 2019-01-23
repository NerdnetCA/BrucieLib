package com.rixonsoft.brucielib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum GameState implements State<BrucieGame> {

    BOOTSPLASH() {
        @Override
        public void update(BrucieGame game) {
            game.splashScene.draw(Gdx.graphics.getDeltaTime());
            if(game.assetManager.update() && game.splashScene.isDone()) {
                game.toNextScene();
                game.gameStateMachine.changeState(GameState.NORMAL_RUN);
            }
        }
    },

    PRELOAD() {
        @Override
        public void update(BrucieGame game) {
            super.update(game);
            boolean loaded = game.assetManager.update();
            if(loaded) {
                if(game.currentScene.isDone()) {
                    game.toNextScene();
                    game.gameStateMachine.changeState(NORMAL_RUN);
                } else {
                    game.gameStateMachine.changeState(GameState.CUE);
                }
            } else {
                if(game.currentScene.isDone()) {
                    game.currentScene.hide();
                    game.currentScene = new LoadingScreen();
                    game.currentScene.configure(game);
                    game.currentScene.show();
                    game.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    game.gameStateMachine.changeState(GameState.LOADING);
                }
            }
        }

        @Override
        public void enter(BrucieGame game) {
            game.nextScene.preload();
        }
    },

    CUE() {
        @Override
        public void update(BrucieGame game) {
            super.update(game);
            if(game.currentScene.isDone()) {
                game.toNextScene();
                game.gameStateMachine.changeState(GameState.NORMAL_RUN);
            }
        }
    },

    LOADING() {
        @Override
        public void update(BrucieGame game) {
            super.update(game);
            if(game.assetManager.update()) {
                game.toNextScene();
                game.gameStateMachine.changeState(GameState.NORMAL_RUN);
            }
        }
    },

    NORMAL_RUN() {
    };

    @Override
    public void update(BrucieGame game) {
        game.currentScene.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void enter(BrucieGame game) {

    }

    @Override
    public void exit(BrucieGame game) {

    }

    @Override
    public boolean onMessage(BrucieGame game, Telegram tele) {
        return false;
    }

}
