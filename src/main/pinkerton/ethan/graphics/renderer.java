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

package pinkerton.ethan.graphics;

import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.KHRDebug;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.GLDebugMessageCallbackI;
import pinkerton.ethan.window.window;
import pinkerton.ethan.window.dimensions;

class debug_extension implements GLDebugMessageCallbackI {
	@Override
	public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
		if (severity == KHRDebug.GL_DEBUG_SEVERITY_HIGH || severity == KHRDebug.GL_DEBUG_SEVERITY_MEDIUM)
			System.out.printf("error: GL callback message, %s.\n", GLDebugMessageCallback.getMessage(length, message));
	}
}

public final class renderer {
	public static renderer create(final window window_handle) {
		GLCapabilities capabilities = GL.createCapabilities();
		renderer result = new renderer();

		/*
		 * This renderer uses functions present in the versions 2 and lower.
		 * Validate that the GPU supports these features.
		 */
		if (!capabilities.OpenGL30) {
			System.err.printf("error: video card does not support OpenGL version 3 or greater, required features not present.\n");
			return null;
		}

		/* Set the viewport based on the window's dimensions. */
		dimensions dimensions = new dimensions();
		dimensions.size(window_handle);
		GL20.glViewport(0, 0, dimensions.x[0], dimensions.y[0]);

		System.out.printf("info:  GL version %s, %s.\n", GL20.glGetString(GL20.GL_VERSION), GL20.glGetString(GL20.GL_RENDERER));
		result.handle_debug(capabilities);
		return result;
	}
	public void handle_debug(GLCapabilities capabilities) {
		if (capabilities.GL_KHR_debug) {
			KHRDebug.glDebugMessageCallback(new debug_extension(), 0l);
			GL20.glEnable(KHRDebug.GL_DEBUG_OUTPUT);
			System.out.printf("info:  enabled debugging with GL_KHR_debug extension.\n");
		}
	}
	public void clear() {
		GL20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		GL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}
	public void draw(vertex_array vao, vertex_buffer vbo, index_buffer ibo, shader shader) {
		shader.bind();
		vao.bind();
		vbo.bind();
		vao.enable();
		ibo.bind();
		GL20.glDrawElements(GL20.GL_TRIANGLES, 6, GL20.GL_UNSIGNED_INT, 0);
		ibo.unbind();
		shader.unbind();
		vbo.unbind();
		vao.disable();
		vao.unbind();
	}
}
