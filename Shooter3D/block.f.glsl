#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;

void main(){
    vec4 temp = texture(texture_sampler, outTexCoord);
    fragColor = vec4(1.0, 1.0, 1.0, 1.0);
}