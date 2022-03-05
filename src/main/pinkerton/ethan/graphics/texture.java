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

import pinkerton.ethan.util.image;
import org.lwjgl.opengl.GL20;

public final class texture {
	public int id;

	public static texture create(final String path) {
		texture result = new texture();
		image data;

		/* Generate our ID. */
		result.id = GL20.glGenTextures();

		/* Try and load the data. */
		try  {
			data = new image(path);
		} catch (Exception e) {
			System.err.printf("error: loading texture threw exception on file %s, %s.\n", path, e.toString());
			return null;
		}

		/* Only accept channels with RGB or RGBA. */
		if (data.channels < 3) {
			System.err.printf("error: texture wrapper only handles RGB or RGBA, %d channels.\n", data.channels);
			return null;
		}

		/* Translate the channel count to the integer values used by opengl. */
		final int format = GL20.GL_RGB + (data.channels - 3);

		/* Bind and upload our data to the texutre handle. */
		result.bind();
		GL20.glTexImage2D(GL20.GL_TEXTURE_2D, 0, format, data.width, data.height, 0, format, GL20.GL_UNSIGNED_BYTE, data.data);	
	
		result.unbind();
		return result;
	}
	public void bind(final int slot_offset) {
		/* Assumes that the slot boundaries have been checked by the caller, slot should be automatically assigned. */
		GL20.glActiveTexture(GL20.GL_TEXTURE0 + slot_offset);
		GL20.glBindTexture(GL20.GL_TEXTURE_2D, id);

	}
	public void bind() {
		GL20.glBindTexture(GL20.GL_TEXTURE_2D, id);
	}
	public void unbind() {
		GL20.glBindTexture(GL20.GL_TEXTURE_2D, 0);
	}
	public void delete() {
		GL20.glDeleteTextures(id);
	}
}
