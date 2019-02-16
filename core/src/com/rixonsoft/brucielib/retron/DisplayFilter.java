package com.rixonsoft.brucielib.retron;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

class DisplayFilter implements Disposable {

    private final ShaderProgram shaderProgram;

    private final int SHADER_BRITE, SHADER_GRAY;

    private float valBrite=1f;
    private float valGray=1f;

    public DisplayFilter(String vertShader, String fragShader) {
        String vShade = Gdx.files.internal(vertShader).readString();
        String fShade = Gdx.files.internal(fragShader).readString();
        shaderProgram = new ShaderProgram(vShade,fShade);
        if(!shaderProgram.isCompiled()) {
            Gdx.app.log(TAG,shaderProgram.getLog());
        }
        SHADER_BRITE = shaderProgram.getUniformLocation("brite");
        SHADER_GRAY = shaderProgram.getUniformLocation("gray");
        shaderProgram.pedantic = false;
    }

    public void setUniforms() {
        shaderProgram.setUniformf(SHADER_BRITE, valBrite);
        shaderProgram.setUniformf(SHADER_GRAY,valGray);
    }

    public void setBrite(float brite) {
        valBrite = brite;
    }
    public void setGray(float gray) {
        valGray = gray;
    }
    public float getBrite() {
        return valBrite;
    }
    public float getGray() {
        return valGray;
    }

    @Override
    public void dispose() {
        if(shaderProgram != null) shaderProgram.dispose();
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    private static final String TAG = "DISPLAYFILTER";
}
