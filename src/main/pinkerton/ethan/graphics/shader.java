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

import pinkerton.ethan.util.file_handler;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL20;

public final class shader {
    private int id;

    private static boolean validate_compiler(final int glue, final String path) {
		if (GL20.glGetShaderi(glue, GL20.GL_COMPILE_STATUS) == 0) {
			System.out.printf("%s\n", GL20.glGetShaderInfoLog(glue));
			return false;
		}
        return true;
    }
    private static boolean validate_link(final int program) {
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0) {
            System.out.println(GL20.glGetProgramInfoLog(program));
            return false;
        }
        return true;
    }

    private static int create_single(final String path, final int type) {
        file_handler file_handler;
        int glue;
        String source;

        if ((file_handler = pinkerton.ethan.util.file_handler.create(path, true)) == null) {
            System.out.printf("error: cannot read shader %s, invalid path or invalid permissions.\n", path);
            return -1;
        }
        if ((source = file_handler.read()) == null) {
            System.out.printf("error: failed to read shader %s, no bytes received.\n", path);
            return -1;
        }
        /* Create the shader and attach the source. */
        glue = GL20.glCreateShader(type);
        GL20.glShaderSource(glue, source);
		GL20.glCompileShader(glue);

        /* Validate that the compilation was successful. */
        if (!validate_compiler(glue, path))
            return -1;
        return glue;
    }
    public static shader create(final String vertex_path, final String fragment_path) {
        shader result = new shader();
        result.id = GL20.glCreateProgram();

        /* Compile the individual shaders, link them with the shader program. */
        int glue[] = { shader.create_single(vertex_path, GL20.GL_VERTEX_SHADER), shader.create_single(fragment_path, GL20.GL_FRAGMENT_SHADER) };
        if (glue[0] == -1 || glue[1] == -1)
            return null;

        GL20.glAttachShader(result.id, glue[0]);
        GL20.glAttachShader(result.id, glue[1]);
        GL20.glLinkProgram(result.id);

        /* Validate that the link was successful. */
        if (!shader.validate_link(result.id)) {
            return null;
        }
        /* Delete the glue. */
        for (int g : glue) {
			GL20.glDetachShader(result.id, g);
        }
		for (int g : glue) {
			GL20.glDeleteShader(g);
		}
        return result;
    }
    public void enable() {
        GL20.glUseProgram(id);
    }
    public void disable() {
        GL20.glUseProgram(0);
    }
    public void destroy() {
        GL20.glDeleteProgram(id);
    }
}
