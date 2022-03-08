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

import org.lwjgl.opengl.GL30;
import java.util.Queue;
import java.util.LinkedList;


public final class vertex_array implements buffer {
	public int id;
	public int accumilation;
	public Queue<vertex_array_attribute> attributes;

	public vertex_array() {
		attributes = new LinkedList<vertex_array_attribute>();
		accumilation = 0;
	}

	public static vertex_array create() {
		vertex_array result = new vertex_array();	
		result.id = GL30.glGenVertexArrays();
		return result;
	}
	public boolean push(final vertex_array_attribute insertion) {
		accumilation += insertion.stride;
		return attributes.add(insertion);
	}
	public void pop() {
		attributes.remove();
	}
	public void bind() {
		GL30.glBindVertexArray(id);
	}
	public void enable() {
		/* As each data is packed, by using the last ones offset you can calculate the next ones offset aswell, this starts at 0. */
		long accumilative_offset = 0;
		int index = 0;

		/* Bind and enables all the vertex attribute pointers. */
		for (vertex_array_attribute a : attributes) {
			GL30.glVertexAttribPointer(index, a.count, a.type, false, accumilation, accumilative_offset);
			GL30.glEnableVertexAttribArray(index);
			accumilative_offset += a.stride;
			++index;
		}
	}
	public void unbind() {
		GL30.glBindVertexArray(0);
	}
	public void destroy() {
		GL30.glDeleteVertexArrays(id);
	}
}
