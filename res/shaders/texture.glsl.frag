#version 330 core

in vec2 tex_position;

uniform vec4 in_color;
uniform sampler2D in_sampler;

out vec4 out_color;

void main() {
	out_color = in_color * texture(in_sampler, tex_position);
}



