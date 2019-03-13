package com.rixonsoft.brucielib.retron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class RetronBatch extends SpriteBatch {


    private final int width, height;
    private final FrameBuffer framebuffer;
    private final OrthographicCamera fbcamera;
    private final OrthographicCamera dispcamera;
    private final DisplayFilter displayFilter;
    private float bgRed, bgGreen, bgBlue;


    public RetronBatch(int width, int height) {
        super();
        this.width = width;

        this.height = height;
        // Video framebuffer of exactly the specified resolution
        framebuffer = new FrameBuffer(Pixmap.Format.RGBA8888,width,height,false);
        // Main camera for rendering the video framebuffer
        fbcamera = new OrthographicCamera();
        fbcamera.setToOrtho(true,width,height);
        dispcamera = new OrthographicCamera();
        dispcamera.setToOrtho(false,width,height);
        // OpenGL shader for filtering the framebuffer when rendering to display
        displayFilter = new DisplayFilter();

    }

    public DisplayFilter getDisplayFilter() {
        return displayFilter;
    }

    public void begin() {
        framebuffer.begin();
        Gdx.gl20.glClearColor(bgRed,bgGreen,bgBlue,1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fbcamera.update();
        setProjectionMatrix(fbcamera.combined);
        super.begin();
    }

    public void end() {
        super.end();
        framebuffer.end();

        dispcamera.update();
        setProjectionMatrix(dispcamera.combined);
        super.begin();
        setShader(displayFilter.getShaderProgram());
        draw(framebuffer.getColorBufferTexture(),0,0);
        super.end();
    }

    public void dispose() {
        framebuffer.dispose();
        displayFilter.dispose();
    }
}
