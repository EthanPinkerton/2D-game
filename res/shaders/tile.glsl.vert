#version 330 core

layout (location = 0) in vec3 i_position;
layout (location = 1) in vec2 i_uv_position;
layout (location = 2) in vec2 i_offset;


out vec2 p_uv_position;
uniform mat4 in_projection;

void main() {
    vec4 result = vec4(i_position, 1.0) + vec4(i_offset, 0.0, 0.0);
    gl_Position = in_projection * result;
    p_uv_position = i_uv_position;
}