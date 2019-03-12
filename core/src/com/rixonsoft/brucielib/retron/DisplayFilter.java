package com.rixonsoft.brucielib.retron;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

public class DisplayFilter implements Disposable {

    private final ShaderProgram shaderProgram;

    private final int SHADER_BRITE, SHADER_COLOR, SHADER_TINT, SHADER_VIGNETTE_RADIUS;
    private final int SHADER_VIGNETTE_SOFTNESS, SHADER_VIGNETTE_ALPHA;

    private float valBrite=1f;
    private float valColor =1f;
    private float valTint=0f;
    private float valVignetRadius =0.8f;
    private float valVignetSoft = 0.4f;
    private float valVignetAlpha = 0.7f;

    public DisplayFilter() {
        String fragShader = "brucie/glsl/frag_retron.glsl";
        String vertShader = "brucie/glsl/vert_nul.glsl";
        String vShade = Gdx.files.internal(vertShader).readString();
        String fShade = Gdx.files.internal(fragShader).readString();
        shaderProgram = new ShaderProgram(vShade,fShade);
        if(!shaderProgram.isCompiled()) {
            Gdx.app.log(TAG,shaderProgram.getLog());
        }
        SHADER_BRITE = shaderProgram.getUniformLocation("u_brite");
        SHADER_COLOR = shaderProgram.getUniformLocation("u_color");
        SHADER_TINT = shaderProgram.getUniformLocation("u_tint");
        SHADER_VIGNETTE_RADIUS = shaderProgram.getUniformLocation("u_vignette_radius");
        SHADER_VIGNETTE_SOFTNESS = shaderProgram.getUniformLocation("u_vignette_softness");
        SHADER_VIGNETTE_ALPHA = shaderProgram.getUniformLocation("u_vignette_alpha");
        //shaderProgram.pedantic = false;
    }

    public void setUniforms() {
        shaderProgram.setUniformf(SHADER_BRITE, valBrite);
        shaderProgram.setUniformf(SHADER_COLOR, valColor);
        shaderProgram.setUniformf(SHADER_TINT,valTint);
        shaderProgram.setUniformf(SHADER_VIGNETTE_RADIUS, valVignetRadius);
        shaderProgram.setUniformf(SHADER_VIGNETTE_SOFTNESS, valVignetSoft);
        shaderProgram.setUniformf(SHADER_VIGNETTE_ALPHA, valVignetAlpha);
    }

    public void setBrite(float brite) {
        valBrite = brite;
    }
    public void setSaturation(float value) {
        valColor = value;
    }
    public float getBrite() {
        return valBrite;
    }
    public float getSaturation() {
        return valColor;
    }
    public void setTint(float tint) {
        valTint = tint;
    }
    public float getTint() {
        return valTint;
    }
    public void setVRad(float vrad) {
        valVignetRadius = vrad;
    }
    public float getVignetteSoftness() {
        return valVignetSoft;
    }
    public void setVignetteSoftness(float vignetSoft) {
        this.valVignetSoft = vignetSoft;
    }
    public float getVignetteAlpha() {
        return valVignetAlpha;
    }
    public void setVignetteAlpha(float vignetAlpha) {
        this.valVignetAlpha = vignetAlpha;
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
