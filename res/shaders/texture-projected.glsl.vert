#version 330 core

layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_tex_position;
layout (location = 2) in vec3 in_color;

out vec2 tex_position;
out vec3 apply_color;
uniform mat4 projection;

void main() {
	tex_position = in_tex_position;
	gl_Position = projection * vec4(in_position, 1.0f);
	apply_color = in_color;
}
