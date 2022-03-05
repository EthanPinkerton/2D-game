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

package pinkerton.ethan.util;

import java.nio.ByteBuffer;
import java.io.IOException;
import org.lwjgl.stb.STBImage;

/* Stores data about a loaded image using STB. */
public final class image {
	public int  width;
	public int  height;
	public int  channels;
	public int  format;
	public ByteBuffer data;

	public image(final String path) throws IOException {
		int w[] = new int[1];
		int h[] = new int[1];
		int c[] = new int[1];
		if (!STBImage.stbi_info(path, w, h, c)) {
			throw new IOException("STB image error, check file exists and data integrity");
		}
		data      = STBImage.stbi_load(path, w, h, c, 0);
		width     = w[0];
		height    = h[0];
		channels  = c[0];
	}
}
