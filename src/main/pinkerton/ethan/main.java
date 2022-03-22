/*
 * Copyright 2022 Ethan Pinkerton, Alistair Bell
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package pinkerton.ethan;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import pinkerton.ethan.window.*;
import pinkerton.ethan.graphics.*;
import pinkerton.ethan.scene.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

public final class main {
	public static final String assets = "res";

	public static void main(String[] argv) {
		window   w = window.create("Hello World", 1080, 720, false);
		renderer r = renderer.create(w);
		shader   s = shader.create(String.format("%s/shaders/texture-projected.glsl.vert", assets), String.format("%s/shaders/texture-projected.glsl.frag", assets));
		texture  t = texture.create(String.format("%s/textures/mosaic.jpeg", assets));
		orthographic_camera o = new orthographic_camera(new Vector3f(0f, 0f, 0f), 0f, -1f, 1f, -1f, 1f);

		if (s == null || w == null || r == null) {
			return;
		}

		final float vertices[] = { 0.5f, 0.5f, 0.0f,    1.0f, 1.0f,
				                   0.5f, -0.5f, 0.0f,   1.0f, 0.0f,
				                   -0.5f, -0.5f, 0.0f,  0.0f, 0.0f,
				                   -0.5f, 0.5f, 0.0f,   0.0f, 1.0f };
		vertex_array vao = vertex_array.create();
		vao.bind();
		vertex_buffer vbo = vertex_buffer.create(vertices, true);
		vao.push(new vertex_array_attribute(3, 0));
		vao.push(new vertex_array_attribute(2, 12));
		vao.enable();
		s.bind();

		final int indexes[] = { 0, 1, 2, 2, 3, 0 };
		index_buffer ibo = index_buffer.create(indexes, true);

		int color_location = s.get_uniform("in_color");
		if (color_location != -1) {
			GL30.glUniform4f(color_location, 1.0f, 0.0f, 0.0f, 1.0f);
		}

		s.upload_mat4f(o.view_projection, "projection");

		while (!GLFW.glfwWindowShouldClose(w.handle)) {
			r.clear();
			t.bind();
			r.draw(vao, vbo, ibo, s);
			t.unbind();

			GLFW.glfwSwapBuffers(w.handle);
			GLFW.glfwPollEvents();

			if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
				GL30.glPolygonMode(GL30.GL_FRONT_AND_BACK, GL30.GL_LINE);
			} else if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_U) == GLFW.GLFW_PRESS) {
				GL30.glPolygonMode(GL30.GL_FRONT_AND_BACK, GL30.GL_FILL);
			}

			s.bind();
			float angle = ((float)GLFW.glfwGetTime()) % 360;
			o.set_rotation(angle);
			s.upload_mat4f(o.view_projection, "projection");
			GL30.glUniform4f(color_location, (float)(Math.sin(GLFW.glfwGetTime())), 0.3f, 1.0f, 1.0f);
			s.unbind();
		}
		vao.disable();
		vao.unbind();
		ibo.unbind();

		vao.destroy();
		vbo.destroy();
		ibo.destroy();
		w.destroy();
		s.destroy();
		t.destroy();
	}
}