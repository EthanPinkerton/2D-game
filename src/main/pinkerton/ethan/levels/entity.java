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
import org.joml.Vector4f;
import pinkerton.ethan.graphics.*;

public class entity {
    public Matrix4f model_matrix;
    public Vector3f position;

    public entity() {
        model_matrix = new Matrix4f().identity();
        position     = new Vector3f();
    }
    public void calculate() {
        model_matrix = model_matrix.translate(position);
    }
    public void draw(shader program, final Matrix4f camera_projection, final int texture_slot) {
    }
}
