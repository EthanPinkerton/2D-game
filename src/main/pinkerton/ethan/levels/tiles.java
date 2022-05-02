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

package pinkerton.ethan.levels;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL33;
import pinkerton.ethan.graphics.*;
import org.lwjgl.opengl.GL30;

public final class tiles {
    public vertex_array  vao;
    public vertex_buffer sbo;
    public shader        program;
    public texture       texture_map;
    public int           tile_count;

    public tiles(final vertex_buffer default_quad, final index_buffer default_indices, final float[] tile_data, final int members, String asset_path) {
        if ((vao = vertex_array.create()) == null) {
            return;
        }
        default_indices.bind();
        if ((sbo = vertex_buffer.create(tile_data, members * 4, true)) == null) {
            vao.destroy();
            return;
        }
        if ((program = shader.create(String.format("%s/shaders/tile.glsl.vert", asset_path), String.format("%s/shaders/tile.glsl.frag", asset_path))) == null) {
            vao.destroy();
            sbo.destroy();
            return;
        }
        if ((texture_map = texture.create(String.format("%s/textures/tilemap.png", asset_path))) == null) {
            vao.destroy();
            sbo.destroy();
            program.destroy();
            return;
        }

        vao.push(new vertex_array_attribute(3, GL30.GL_FLOAT));
        vao.push(new vertex_array_attribute(2, GL30.GL_FLOAT));
        vao.push(new vertex_array_attribute(2, GL30.GL_FLOAT));
        vao.push(new vertex_array_attribute(2, GL30.GL_FLOAT));
        vao.enable_instanced(2, default_quad, sbo);
        tile_count = members;

        texture_map.bind();
        program.bind();
        program.upload_int(0, "in_sampler");
        program.unbind();
    }
    public void draw(final Matrix4f camera_projection, final int texture_slot) {
        vao.bind();
        texture_map.bind(texture_slot);
        program.bind();
        program.upload_int(texture_slot, "in_sampler");
        program.upload_mat4f(camera_projection, "in_projection");
        GL33.glDrawElementsInstanced(GL33.GL_TRIANGLES, 6, GL33.GL_UNSIGNED_INT, 0, tile_count);
    }
    public void destroy() {
        vao.destroy();
        sbo.destroy();
        texture_map.destroy();
        program.destroy();
    }
}
