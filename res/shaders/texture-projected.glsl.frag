#version 330 core

in vec2 tex_position;
in vec3 apply_color;

uniform sampler2D in_sampler;

out vec4 out_color;

void main() {
	out_color = texture(in_sampler, tex_position) * vec4(apply_color, 1.0f);
}



