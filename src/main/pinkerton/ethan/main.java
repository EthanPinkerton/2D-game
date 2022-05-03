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

import java.util.Arrays;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import pinkerton.ethan.window.*;
import pinkerton.ethan.graphics.*;
import pinkerton.ethan.scene.*;
import pinkerton.ethan.levels.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import java.lang.reflect.Array;
import java.util.Objects;

class generated {
	public float data[];
	public int members;
	public generated(final float data[], final int members) {
		this.data = data;
		this.members = members;
	}
}

class key_callback implements GLFWKeyCallbackI {
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		switch (key) {
			case GLFW.GLFW_KEY_P: {
				GL30.glPolygonMode(GL30.GL_FRONT_AND_BACK, GL30.GL_LINE);
				break;
			}
			case GLFW.GLFW_KEY_O: {
				GL30.glPolygonMode(GL30.GL_FRONT_AND_BACK, GL30.GL_FILL);
				break;
			}
		}
	}
}

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

	public static generated generate_instance_data() {
		loader l = new loader(String.format("%s/levels/sample.map", assets));
		l.load();
		float data[] = new float[4 * l.columns * l.rows];
		char it;
		int i = 0;
		while ((it = l.iterate()) != '\0') {
			int x = (l.iterator - 1) % l.columns;
			int y = (l.iterator - 1) / l.columns;
			Vector4f constructed;
			if ((constructed = loader.construct(it, x, y)) != null) {
				data[4 * i + 0] = constructed.x;
				data[4 * i + 1] = constructed.y;
				data[4 * i + 2] = constructed.z;
				data[4 * i + 3] = constructed.w;
				++i;
			}
		}
		return new generated(Arrays.copyOf(data, i * 4), i);
	}

	public static void main(String[] argv) {
		window   w = window.create("2D Portal", 1440, 810, false);
		renderer r = renderer.create(w);
		orthographic_camera o = new orthographic_camera(new Vector3f(-0, 0, 0f), 0, -10.0f, 10f, -10f, 10f);
		shader es = shader.create("res/shaders/entity.glsl.vert", "res/shaders/entity.glsl.frag");

		generated g = generate_instance_data();
		vertex_buffer quad_vbo = vertex_buffer.create(vbo_data, true);
		index_buffer  quad_ibo = index_buffer.create(ibo_data, true);
		tiles t = new tiles(quad_vbo, quad_ibo, g.data, g.members, "res");
		player p = new player(quad_vbo, quad_ibo, "res");

		GLFW.glfwSetKeyCallback(w.handle, new key_callback());
		o.calculate();
		p.position.x -= 1f;
		p.calculate();


		float view = 10f;
		double current_time = 0.0f, last_time = 0.0f, delta_time;
		while (!GLFW.glfwWindowShouldClose(w.handle)) {
			current_time = GLFW.glfwGetTime();
			delta_time = current_time - last_time;
			last_time = current_time;
			r.clear();

			p.rotation += delta_time * 20f;
			float v = (float)(Math.sin(GLFW.glfwGetTime())) * 3;
			p.scale = new Vector3f(v, v, 2);
			p.calculate();

			p.draw(es, o.view_projection, 0);
			es.upload_vec4(new Vector4f(v / 3, v / 2, -v, 1), "in_color");

			if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) {
				p.position.x -= 10 * delta_time;
				p.calculate();
			}
			if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS) {
				p.position.x += 10 * delta_time;
				p.calculate();
			}
			if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
				o.position.x -= 5 * delta_time;
				o.calculate();
			}
			if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
				o.position.x += 5 * delta_time;
				o.calculate();
			}
			if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_E) == GLFW.GLFW_PRESS) {
				o.rotation -= 20 * delta_time;
				o.calculate();
			}
			if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_Q) == GLFW.GLFW_PRESS) {
				o.rotation += 20 * delta_time;
				o.calculate();
			}
			if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
				o.set_projection(-view, view, -view, view);
				view -= 3f * delta_time;
				o.calculate();
			}
			if (GLFW.glfwGetKey(w.handle, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
				o.set_projection(-view, view, -view, view);
				view += 3f * delta_time;
				o.calculate();
			}

			t.draw(o.view_projection, 1);
			t.program.upload_int(1, "in_greyscale");
			GLFW.glfwSwapBuffers(w.handle);
			GLFW.glfwPollEvents();
		}
		t.destroy();
	}
}
