package com.rixonsoft.brucielib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum SceneState implements State<Scene> {
    FADEIN() {
        @Override
        public float getFadeValue(float time) {
            if(time > 1.0f) time = 1.0f;
            return 1f-time;
        }

        @Override
        public void enter(Scene entity) {
            Gdx.app.log(TAG,"Entering fadein");
            entity.beginFade();
        }

        @Override
        public void update(Scene entity) {
            super.update(entity);
            if(entity.renderFade(Gdx.graphics.getDeltaTime())) {
                entity.stateMachine.changeState(SceneState.NORMAL_RUN);
            }
        }
    },

    FADEOUT() {
        @Override
        public void enter(Scene entity) {
            entity.beginFade();
        }

        @Override
        public void update(Scene entity) {
            super.update(entity);
            if(entity.renderFade(Gdx.graphics.getDeltaTime())) {
                entity.setDone(true);
                entity.stateMachine.changeState(SceneState.DONE);
            }
        }
    },

    DONE() {
        @Override
        public float getFadeValue(float time) {
            return 1.0f;
        }
        @Override
        public void update(Scene entity) {
            super.update(entity);
            entity.renderFade(Gdx.graphics.getDeltaTime());
        }
    },

    NORMAL_RUN() {
    };

    private static final String TAG="SCENESTATE";

    @Override
    public void enter(Scene entity) {

    }

    @Override
    public void update(Scene entity) {
        entity.draw(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void exit(Scene entity) {

    }

    @Override
    public boolean onMessage(Scene entity, Telegram telegram) {
        return false;
    }

    public float getFadeValue(float time) {
        if(time > 1.0f) time = 1.0f;
        return time;
    }
}
