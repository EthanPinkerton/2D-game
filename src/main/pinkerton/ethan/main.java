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

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import pinkerton.ethan.window.*;
import pinkerton.ethan.graphics.*;
import pinkerton.ethan.scene.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

public final class main {
	public static final String assets = "res";


	public static final float vbo_data[] = {
			-1.0f,   1.0f,  0.0f,  0.0f,  1.0f,
			 1.0f,   1.0f,  0.0f,  1.0f,  1.0f,
       		 1.0f,  -1.0f,  0.0f,  1.0f,  0.0f,
			-1.0f,  -1.0f,  0.0f,  0.0f,  0.0f,
	};
	public static int ibo_data[] = {
		0, 1, 2, 2, 3, 0
	};

	public static float[] generate_instance_data(int rows, int columns) {
		float data[] = new float[4 * (rows * columns)];
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				int target_texture = (int) (Math.random() * 36);
				Vector2f offset = texture.calculate_offsets(6, target_texture);
				int data_offset = ((i * rows) + j) * 4;
				data[data_offset + 0] =  2.0f * j;
				data[data_offset + 1] = -2.0f * i;
				data[data_offset + 2] = offset.x;
				data[data_offset + 3] = offset.y;
			}
		}
		return data;
	}

	public static void main(String[] argv) {
		window   w = window.create("Hello World", 1440, 810, false);
		renderer r = renderer.create(w);
		shader   s = shader.create(String.format("%s/shaders/tile.glsl.vert", assets), String.format("%s/shaders/tile.glsl.frag", assets));
		texture  t = texture.create(String.format("%s/textures/tilemap.png", assets));
		orthographic_camera o = new orthographic_camera(new Vector3f(0f, 0f, 0f), 0, -10.0f, 10f, -10f, 10f);

		vertex_array  vao = vertex_array.create();
		vertex_buffer vbo = vertex_buffer.create(vbo_data, true);
		vertex_buffer sbo = vertex_buffer.create(generate_instance_data(5, 5), true);
		index_buffer  ibo = index_buffer.create(ibo_data, true);
		vao.push(new vertex_array_attribute(3, GL30.GL_FLOAT));
		vao.push(new vertex_array_attribute(2, GL30.GL_FLOAT));
		vao.push(new vertex_array_attribute(2, GL30.GL_FLOAT));
		vao.push(new vertex_array_attribute(2, GL30.GL_FLOAT));
		vao.enable_instanced(2, vbo, sbo);

		s.bind();
		o.calculate();
		s.upload_mat4f(o.view_projection, "in_projection");

		t.bind(0);
		s.upload_int(0, "in_sampler");

		while (!GLFW.glfwWindowShouldClose(w.handle)) {
			r.clear();
			GL33.glDrawElementsInstanced(GL30.GL_TRIANGLES, ibo_data.length, GL30.GL_UNSIGNED_INT, 0, 25);
			GLFW.glfwSwapBuffers(w.handle);
			GLFW.glfwPollEvents();
		}

		vao.destroy();
		vbo.destroy();
		ibo.destroy();
		s.destroy();
		t.destroy();
	}
}
