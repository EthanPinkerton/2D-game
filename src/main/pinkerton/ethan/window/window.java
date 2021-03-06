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

package pinkerton.ethan.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL20;

class resize_callback implements GLFWWindowSizeCallbackI {
	@Override
	public void invoke(final long window, final int width, final int height){
		GL20.glViewport(0,0,width,height);
	}
}

public final class window {
	public long handle;

	public static window create(final String name, final int width, final int height, final boolean resizable) {
		/* Initialise glfw. */
		if (!GLFW.glfwInit()) {
			System.err.printf("error: unable to initialise glfw, cannot create a window.\n");
			return null;
		}
		/* Use the monitor dimensions and the desired width and height to autocentre the window. */
		GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		return window.create(name, width, height, (mode.width() / 2) - (width / 2), (mode.height() / 2) - (height / 2), resizable);
	}
	public static window create(final String name, final int width, final int height, final int xpos, final int ypos, final boolean resizable) {
		window result = new window();

		/* Initialise glfw. */
		if (!GLFW.glfwInit()) {
			System.err.printf("error: unable to initialise glfw, failed to create window.\n");
			return null;
		}

		/* Set some window hints, this is internal magic. */
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, (resizable == true) ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);

		result.handle = GLFW.glfwCreateWindow(width, height, name, 0l, 0l);
		if (result.handle == 0) {
			System.err.printf("error: unable to create glfw window.\n");
			return null;
		}

		GLFW.glfwSetWindowSizeCallback(result.handle, new resize_callback());
		GLFW.glfwMakeContextCurrent(result.handle);
		GLFW.glfwSetWindowPos(result.handle, xpos, ypos);
		GLFW.glfwShowWindow(result.handle);
		return result;
	}
	public void destroy() {
		GLFW.glfwDestroyWindow(handle);
		GLFW.glfwTerminate();
	}
}
