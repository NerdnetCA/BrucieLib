package com.rixonsoft.brucielib.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum WipedState implements State<WipedScene> {
    WIPEIN() {
        @Override
        public float getFadeValue(float wipetime) {
            if(wipetime > 1.0f) wipetime = 1.0f;
            return 1f-wipetime;
        }

        @Override
        public void enter(WipedScene entity) {
            Gdx.app.log(TAG,"Entering fadein");
            entity.wipeTime = 0f;
        }

        @Override
        public void update(WipedScene entity) {
            super.update(entity);
            if(entity.wipeTime >= 1.0f) {
                entity.wipeStateMachine.changeState(WipedState.NORMAL_RUN);
            }
        }
    },

    WIPEOUT() {
        public float getFadeValue(float time) {
            if(time > 1.0f) time = 1.0f;
            return time;
        }
        @Override
        public void enter(WipedScene entity) {
            entity.wipeTime = 0f;
        }

        @Override
        public void update(WipedScene entity) {
            //super.update(entity);
            entity.draw(Gdx.graphics.getDeltaTime());
            if(entity.wipeTime >= 1.0f) {
                entity.wipeStateMachine.changeState(WipedState.DONE);
            }
        }
    },

    DONE() {
        @Override
        public float getFadeValue(float time) {
            return 1.0f;
        }
        @Override
        public void update(WipedScene entity) {
            //super.update(entity);
            entity.draw(Gdx.graphics.getDeltaTime());
        }
        @Override
        public void enter(WipedScene entity) {
            entity.setDone(true);
            Gdx.app.log(TAG,"State is done");

        }
    },

    NORMAL_RUN() {
    };

    private static final String TAG="WIPEDSTATE";

    @Override
    public void enter(WipedScene entity) {

    }

    @Override
    public void update(WipedScene entity) {
        entity.draw(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void exit(WipedScene entity) {

    }

    @Override
    public boolean onMessage(WipedScene entity, Telegram telegram) {
        return false;
    }

    public float getFadeValue(float time) {
        if(time > 1.0f) time = 1.0f;
        return 0;
    }
}
