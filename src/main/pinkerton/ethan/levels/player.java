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
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import pinkerton.ethan.graphics.*;

public class player extends entity {
    public vertex_array vao;
    public texture      texture_map;
    public float        rotation;
    public Vector3f     scale;

    public player(final vertex_buffer default_quad, final index_buffer default_indices, final String asset_path) {
        super();
        scale = new Vector3f(1f, 1f, 1f);
        rotation = 0f;

        if ((vao = vertex_array.create()) == null)
            return;
        if ((texture_map = texture.create(String.format("%s/textures/mosaic.jpeg", asset_path))) == null) {
            vao.destroy();
            return;
        }
        default_quad.bind();
        default_indices.bind();
        vao.push(new vertex_array_attribute(3, GL30.GL_FLOAT));
        vao.push(new vertex_array_attribute(2, GL30.GL_FLOAT));
        vao.enable();
    }

    @Override
    public void calculate() {
        model_matrix = new Matrix4f().identity().translate(new Vector3f(-position.x, -position.y, position.z));
        Matrix4f rotation_matrix = new Matrix4f().identity().rotate((float)Math.toRadians((float)-rotation), new Vector3f(0f, 0f, 1f));
        model_matrix.mul(rotation_matrix, model_matrix);
        model_matrix.scale(scale);
    }
    @Override
    public void draw(shader program, final Matrix4f camera_projection, final int texture_slot) {
        program.bind();
        texture_map.bind(texture_slot);
        program.upload_int(texture_slot, "in_sampler");
        program.upload_mat4f(model_matrix, "in_model");
        program.upload_mat4f(camera_projection, "in_projection");
        GL33.glDrawElements(GL30.GL_TRIANGLES, 6 , GL30.GL_UNSIGNED_INT, 0);
    }
}
