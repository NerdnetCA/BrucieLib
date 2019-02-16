

varying vec4 v_color;
varying vec2 v_texCoord0;


uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform float brite;
uniform float gray;

/* Value/Saturation shader */

/* rgb to hsv tool. Not mine */
vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}
/* hsv to rgb tool. Not mine */
vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

vec3 RGBtoYIQ(vec3 RGB){
	const mat3 yiqmat = mat3(
		0.2989, 0.5870, 0.1140,
		0.5959, -0.2744, -0.3216,
		0.2115, -0.5229, 0.3114);
	return RGB * yiqmat;
}

vec3 YIQtoRGB(vec3 YIQ){
	const mat3 rgbmat = mat3(
		1.0, 0.956, 0.6210,
		1.0, -0.2720, -0.6474,
		1.0, -1.1060, 1.7046);
	return YIQ * rgbmat;
}

/* Basic value and saturation modifier.

 */
void main() {


    vec2 tc = v_texCoord0;
    vec4 color_in = texture2D(u_texture, tc);

    vec3 col = color_in.rgb;

    //vec3 hsv = rgb2hsv(col);
    //hsv.y *= gray;
    //hsv.z *= brite;
    //col = hsv2rgb(hsv);

    vec3 yiq = RGBtoYIQ(col);
    yiq.x *= brite;
    yiq.y *= gray;
    yiq.z *= gray*0.8;
    col = YIQtoRGB(yiq);



    gl_FragColor = vec4(col,color_in.a);
}
