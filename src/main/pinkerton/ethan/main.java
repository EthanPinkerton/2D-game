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

import pinkerton.ethan.window.*;
import pinkerton.ethan.graphics.*;
import pinkerton.ethan.maths.*;
import pinkerton.ethan.util.*;
import java.util.Arrays;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

public final class main {
	public static final String assets = "res";

	public static void main(String[] argv) {
		window   w = window.create("Hello World", 1080, 720, false);
		renderer r = renderer.create(w);
		shader   s = shader.create(String.format("%s/shaders/default.glsl.vert", assets), String.format("%s/shaders/default.glsl.frag", assets));
		texture  t = texture.create(String.format("%s/textures/mosic.jpeg", assets));

		if (s == null || w == null || r == null) {
			System.out.println("Hello world\n");
		}


		final float vertices[] = { 0.5f,  0.5f, 0.0f, 0.5f, -0.5f, 0.0f, -0.5f, -0.5f, 0.0f, -0.5f,  0.5f, 0.0f };
		vertex_array vao = vertex_array.create();
		vao.bind();
		vertex_buffer vbo = vertex_buffer.create(vertices, true);
		vbo.bind();
		GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, true, 0, 0);
		GL30.glEnableVertexAttribArray(0);

		vbo.unbind();
		vao.unbind();
		s.bind();

		final int indexes[] = { 0, 1, 2, 2, 3, 0 };
		index_buffer ibo = index_buffer.create(indexes, true);

		int location = s.get_uniform("in_color");
		if (location != -1) {
			GL30.glUniform4f(location, 1.0f, 0.0f, 0.5f, 1.0f);
		}

		while (!GLFW.glfwWindowShouldClose(w.handle)) {
			r.clear();

			vao.bind();
			ibo.bind();
			GL30.glEnableVertexAttribArray(0);

			GL30.glDrawElements(GL30.GL_TRIANGLES, 6, GL30.GL_UNSIGNED_INT, 0);

			GLFW.glfwSwapBuffers(w.handle);
			GLFW.glfwPollEvents();

			GL30.glUniform4f(location, (float)Math.sin(GLFW.glfwGetTime()), 0.3f, 0.3f, 1.0f);
		}
		vao.unbind();
		ibo.unbind();

		vao.destroy();
		vbo.destroy();
		ibo.destroy();
		w.destroy();
		s.destroy();
	}
}
