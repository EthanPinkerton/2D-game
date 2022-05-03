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

import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class orthographic_camera extends camera {
    public Matrix4f projection;
    public Matrix4f view;
    public Matrix4f view_projection;

    public Vector3f position;
    public float rotation;

    public orthographic_camera() {
        projection      = new Matrix4f();
        view            = new Matrix4f();
        view_projection = new Matrix4f();
    }
    public orthographic_camera(final Vector3f position, final float rotation, final float left, final float right, final float bottom, final float top) {
        this.position   = position;
        this.rotation   = rotation;
        view            = new Matrix4f().identity();
        projection      = new Matrix4f().ortho(left, right, bottom, top, -1.0f, 1.0f);
        view_projection = new Matrix4f();
        projection.mul(view, view_projection);
    }
    public void calculate() {
        /* Remove the need for matrix inversion. */
        Matrix4f transform  = new Matrix4f().identity().translate(position);
        Matrix4f rotation   = new Matrix4f().identity().rotation((float)Math.toRadians(-this.rotation), new Vector3f(0, 0, 1));
        view = transform.mul(rotation);
        view_projection = projection.mul(view, view_projection);
    }
	public void set_projection(final float left, final float right, final float bottom, final float top) {
		projection = new Matrix4f().ortho(left, right, bottom, top, -1.0f, 1.0f);
	}
    public Matrix4f get_view() {
        return view_projection;
    }
}
