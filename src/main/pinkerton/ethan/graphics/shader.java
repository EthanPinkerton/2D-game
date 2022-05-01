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

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import pinkerton.ethan.util.file_handler;
import org.lwjgl.opengl.GL20;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.HashMap;

public final class shader {
    private FloatBuffer upload_cache;
    private int id;
    public HashMap<String, Integer> uniform_cache;

    public shader() {
        uniform_cache = new HashMap<String, Integer>();
        upload_cache  = BufferUtils.createFloatBuffer(16);
    }

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
            System.err.printf("error: cannot read shader %s, invalid path or invalid permissions.\n", path);
            return -1;
        }
        if ((source = file_handler.read()) == null) {
            System.err.printf("error: failed to read shader %s, no bytes received.\n", path);
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
        int glue[] = {shader.create_single(vertex_path, GL20.GL_VERTEX_SHADER), shader.create_single(fragment_path, GL20.GL_FRAGMENT_SHADER)};
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

    public int get_uniform(final String name) {
        /* Search the uniform cache for the target. */
        if (uniform_cache.get(name) != null) {
            return Integer.valueOf(uniform_cache.get(name));
        }

        /* Get from the shader program, this will is slower than storing them in cache. */
        int location = GL20.glGetUniformLocation(id, name);

        /* Validate that the uniform could be found. */
        if (GL20.glGetError() != GL20.GL_NO_ERROR) {
            System.err.printf("error: could not find uniform %s in shader program.\n");
            return -1;
        }

        /* Store the name and location within the uniform cache, return the location. */
        uniform_cache.put(name, location);
        return location;
    }

    public int upload_mat4f(final Matrix4f matrix, final String name) {
        int location;
        if ((location = get_uniform(name)) < 0)
            return -1;
        matrix.get(upload_cache);
        GL20.glUniformMatrix4fv(location, false, upload_cache);
        return 0;
    }

    public int upload_int(final int value, final String name) {
        int location;
        if ((location = get_uniform(name)) < 0)
            return -1;
        GL20.glUniform1i(location, value);
        return 0;
    }
    public void bind() {
        GL20.glUseProgram(id);
    }
    public void unbind() {
        GL20.glUseProgram(0);
    }
    public void destroy() {
        GL20.glDeleteProgram(id);
    }
}
