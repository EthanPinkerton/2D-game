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

package pinkerton.ethan.maths;

public final class vec3 {
	public float x, y, z;

	public vec3() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	public vec3(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public vec3(final vec2 xy, final float z) {
		this.x = xy.x;
		this.y = xy.y;
		this.z = z;
	}
	public vec3 add(final vec3 vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		return this;
	}
	public vec3 subtract(final vec3 vector) {
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		return this;
	}
	public vec3 multiply(final vec3 vector) {
		x *= vector.x;
		y *= vector.y;
		z *= vector.z;
		return this;
	}
	public vec3 multiply(final float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}
	public vec3 divide(final vec3 vector) {
		x /= vector.x;
		y /= vector.y;
		z /= vector.z;
		return this;
	}
	public vec3 divide(final float scalar) {
		x /= scalar;
		y /= scalar;
		z /= scalar;
		return this;
	}
	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	public static vec3 unit(final vec3 vector) {
		final double divisor = vector.magnitude();
		return new vec3((float)(vector.x / divisor), ((float)(vector.y / divisor)), ((float)(vector.z / divisor)));
	}
	public double angle() {
		/* TODO find atan3 */
		return 0.0f;
	}
	@Override
	public String toString() {
		return String.format("(%f %f %f)", x, y, z);
	}
}
