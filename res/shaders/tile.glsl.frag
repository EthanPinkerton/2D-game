#version 330 core

out vec4 out_color;
in vec2 p_uv_position;

uniform sampler2D in_sampler;
uniform int in_greyscale;

vec4 default_color() {
    return texture(in_sampler, p_uv_position);
}
vec4 greyscale_color() {
    vec4 tex = default_color();
    float brightness = (tex.r + tex.g + tex.b) / 3.0;
    return vec4(brightness, brightness, brightness, tex.a);
}

void main() {
    if (in_greyscale == 1)
        out_color = greyscale_color();
    else
        out_color = default_color();
}