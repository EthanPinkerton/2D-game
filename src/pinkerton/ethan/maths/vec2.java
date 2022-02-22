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

public final class vec2 {
	public float x, y;

	public vec2() {
		this.x = 0.0f;
		this.y = 0.0f;
	}
	public vec2(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	public vec2 add(final vec2 vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	public vec2 subtract(final vec2 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}
	public vec2 multiply(final vec2 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		return this;
	}
	public vec2 multiply(final float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	public vec2 divide(final vec2 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
		return this;
	}
	public vec2 divide(final float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}
	public double magnitude() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	public static vec2 unit(final vec2 vector) {
		final double divisor = vector.magnitude();
		return new vec2(((float)(vector.x / divisor)), ((float)(vector.y / divisor)));
	}
	public double angle() {
		return Math.toDegrees(Math.atan2(this.y, this.x));
	}
	@Override
	public String toString() {
		return String.format("(%f %f)", this.x, this.y);
	}
}
