package com.rixonsoft.brucielib.retron;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DialogBox extends RetronEntity {


    private final Skin skin;
    public final NinePatch bgPatch;

    public DialogBox(Skin skin) {
        this.skin = skin;
        this.bgPatch = skin.getPatch("frame_dark");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        bgPatch.draw(batch, getX(), getY(), getWidth(), getHeight());
    }


}
