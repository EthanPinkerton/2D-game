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
import java.util.PriorityQueue;

/* Assume that the data is likely to be normalised. */
final class vertex_array_attribute {
	public int stride;
	public int type;
}


public final class vertex_array implements buffer {
	public int id;
	public Queue<vertex_array_attribute> attributes;

	public vertex_array() {
		attributes = new PriorityQueue<vertex_array_attribute>();
	}

	public static vertex_array create() {
		vertex_array result = new vertex_array();	
		result.id = GL30.glGenVertexArrays();
		return result;
	}
	public boolean push(final vertex_array_attribute insertion) {
		return attributes.add(insertion);
	}
	public void pop() {
		attributes.remove();
	}
	public void bind() {
		GL30.glBindVertexArray(id);
	}
	public void unbind() {
		GL30.glBindVertexArray(0);
	}
	public void destroy() {
		GL30.glDeleteVertexArrays(id);
	}
}
