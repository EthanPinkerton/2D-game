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

import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;
import java.nio.IntBuffer;

public final class index_buffer implements buffer {
	private int id;

	public static index_buffer create(final int data[], boolean is_static) {
		index_buffer result = new index_buffer();
		
		/* Create and bind the buffer data. */
		result.id = GL20.glGenBuffers();
		result.bind();
		
		IntBuffer upload_data = MemoryUtil.memAllocInt(data.length);
		upload_data.put(data).flip();

		GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, upload_data, (is_static) ? GL20.GL_STATIC_DRAW : GL20.GL_DYNAMIC_DRAW);
		MemoryUtil.memFree(upload_data);

		/* Unbind. */
		result.unbind();
		return (GL20.glGetError() == GL20.GL_NO_ERROR) ? result : null;
	}

	public void bind() {
		GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, id);
	}
	public void unbind() {
		GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	public void destroy() {
		unbind();
		GL20.glDeleteBuffers(id);
	}
}
