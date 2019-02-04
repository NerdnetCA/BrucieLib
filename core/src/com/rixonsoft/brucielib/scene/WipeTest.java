package com.rixonsoft.brucielib.scene;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.rixonsoft.brucielib.test.TestBundle;
import com.rixonsoft.brucielib.test.TestScene;

public class WipeTest extends WipedScene {
    private TestBundle bundle;

    @Override
    public void preload() {
        bundle = wrangler.wrangle(TestBundle.class);
    }

    @Override
    public void show() {
        super.show();

        bundle.resolveAssets();

        Button b = new TextButton("Button",bundle.defaultSkin);
        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myGame.queueScene(new TestScene());
                actor.setTouchable(Touchable.disabled);
                wipeStateMachine.changeState(WipedState.WIPEOUT);
                //setFadeOut();
            }
        });

        guiStage.addActor(b);
    }
    @Override
    public void draw(float delta) {

    }
}
