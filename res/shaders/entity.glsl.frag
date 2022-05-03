#version 330 core

out vec4 out_color;

in vec2 p_uv_position;
uniform sampler2D in_sampler;
uniform vec4 in_color;

void main() {
    out_color = texture(in_sampler, p_uv_position) * in_color;
}