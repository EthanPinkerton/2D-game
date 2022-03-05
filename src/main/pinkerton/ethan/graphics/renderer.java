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

import pinkerton.ethan.window.window;
import pinkerton.ethan.window.dimensions;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLCapabilities;

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
		dimensions.position(window_handle);
		GL20.glViewport(0, 0, dimensions.x[0], dimensions.y[0]);

		System.out.printf("info:  GL version %s, %s.\n", GL20.glGetString(GL20.GL_VERSION), GL20.glGetString(GL20.GL_RENDERER));
		return result;
	}
}
