package com.rixonsoft.brucielib.retron;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RetronDummy extends RetronEntity {


    private TextureRegion texture;

    public RetronDummy() {
        setTransform(true);
    }

    public RetronDummy setTexture(Texture texture) {
        this.texture = new TextureRegion(texture);
        setOrigin(texture.getWidth()/2f,texture.getHeight()/2f);
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        return this;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {

        //if (true) applyTransform(batch, computeTransform());
        batch.draw(texture,getX(),getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
        //if (true) resetTransform(batch);



    }



}
