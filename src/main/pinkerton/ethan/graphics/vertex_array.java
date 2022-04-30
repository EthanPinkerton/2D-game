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
import org.lwjgl.opengl.GL33;
import java.util.ArrayList;


public final class vertex_array implements buffer {
	public int id;
	public int accumulation;
	public ArrayList<vertex_array_attribute> attributes;

	public vertex_array() {
		attributes = new ArrayList<vertex_array_attribute>();
		accumulation = 0;
	}

	public static vertex_array create() {
		vertex_array result = new vertex_array();
		result.id = GL30.glGenVertexArrays();
		result.bind();
		return result;
	}

	public boolean push(final vertex_array_attribute insertion) {
		accumulation += insertion.count * 4;
		return attributes.add(insertion);
	}

	public void bind() {
		GL30.glBindVertexArray(id);
	}

	private static int type_to_bytes(final int type) {
		switch (type) {
			case GL30.GL_UNSIGNED_BYTE:
				return 1;
			case GL30.GL_BYTE:
				return 1;
			case GL30.GL_UNSIGNED_SHORT:
				return 2;
			case GL30.GL_SHORT:
				return 2;
			case GL30.GL_UNSIGNED_INT:
				return 4;
			case GL30.GL_INT:
				return 4;
			case GL30.GL_FLOAT:
				return 4;
		}
		return 8;
	}

	private void enable(vertex_buffer buffer, final int start_index, final int end_index, final int accumulation) {
		if (buffer != null)
			buffer.bind();
		long pointer = 0;
		for (int i = start_index; i < end_index; ++i) {
			vertex_array_attribute a = attributes.get(i);
			GL30.glVertexAttribPointer(i, a.count, a.type, false, accumulation, pointer);
			GL30.glEnableVertexAttribArray(i);
			pointer += a.count * type_to_bytes(a.type);
		}
	}


	public void enable() {
		enable(null, 0, attributes.size(), accumulation);
	}
	public void enable_instanced(final int instance_attributes_index, vertex_buffer static_buffer, vertex_buffer instance_buffer) {
		int static_accumilation = 0;
		for (int i = 0; i < instance_attributes_index; ++i) {
			vertex_array_attribute a = attributes.get(i);
			static_accumilation += a.count * type_to_bytes(a.type);
		}
		enable(static_buffer, 0, instance_attributes_index, static_accumilation);
		enable(instance_buffer, instance_attributes_index, attributes.size(), accumulation - static_accumilation);
		for (int i = instance_attributes_index; i < attributes.size(); ++i)
			GL33.glVertexAttribDivisor(i, 1);
	}

	public void disable() {
		for (int i = 0; i < attributes.stream().count(); ++i)
			GL30.glDisableVertexAttribArray(i);
	}
	public void unbind() {
		GL30.glBindVertexArray(0);
	}
	public void destroy() {
		GL30.glDeleteVertexArrays(id);
	}
}
