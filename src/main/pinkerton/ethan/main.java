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

import java.util.Arrays;

public final class main {
	public static final String assets = "res";

	public static void main(String[] argv) {
		window   w = window.create("Hello World", 1080, 720, 0, 0, false);
		renderer r = renderer.create(w);
		shader   s = shader.create(String.format("%s/shaders/default.glsl.vert", assets), String.format("%s/shaders/default.glsl.frag", assets));

		mat4x4 m1 = new mat4x4(3.0f);
		mat4x4 m2 = new mat4x4();
		Arrays.fill(m2.data, 3.0f);
		m1 = m1.multiply(m2);

		w.destroy();
		s.destroy();
	}
}
