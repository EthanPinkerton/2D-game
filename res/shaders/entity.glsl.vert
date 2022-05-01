#version 330 core

layout (location = 0) in vec3 i_position;
layout (location = 1) in vec2 i_uv_position;

/* Camera projection. */
uniform mat4 in_projection;
/* Model matrix. */
uniform mat4 in_model;

void main() {
    gl_Position = in_projection * in_view * vec4(i_position, 1.0);
}
