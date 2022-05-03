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

package pinkerton.ethan.scene;

import org.joml.Vector3f;
import org.joml.Matrix4f;

public final class perspective_camera extends camera {
    public Vector3f position;
    public Vector3f forward;
    public Vector3f up;
    public Matrix4f projection;
    public Matrix4f view_projection;
    public float    rotation;

    public perspective_camera(final float fov, final float aspect, final float near_z, final float far_z, final Vector3f position) {
        this.position        = position;
        this.projection      = new Matrix4f().perspective(fov, aspect, near_z, far_z);
        this.view_projection = new Matrix4f().identity();
        this.forward         = new Vector3f(0f, 0f, 1f);
        this.up              = new Vector3f(0f, 1f, 0f);
    }
    public void calculate() {
        Vector3f sum = forward;
        sum.add(position);
        view_projection.lookAt(position, sum, up, view_projection);
        view_projection = projection.mul(view_projection, view_projection);
    }
    public Matrix4f get_view() {
        return view_projection;
    }
}
