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

public class dimensions {
    public int x[];
    public int y[];

    public dimensions() {
        x = new int[1];
        y = new int[1];
    }
    public void size(final window window_handle) {
        GLFW.glfwGetWindowSize(window_handle.handle, x, y);
    }
    public void position(final window window_handle) {
        GLFW.glfwGetWindowPos(window_handle.handle, x, y);
    }
}