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

import java.util.Arrays;

public final class mat4x4 {
	public float data[];

	public mat4x4() {
		data = new float[16];
		Arrays.fill(data, 0.0f);
	}
	public mat4x4(final float diagonal) {
		data = new float[16];
		data[0]  = diagonal;
		data[1]  = 0.0f;
		data[2]  = 0.0f;
		data[3]  = 0.0f;
		data[4]  = 0.0f;
		data[5]  = diagonal;
		data[6]  = 0.0f;
		data[7]  = 0.0f;
		data[8]  = 0.0f;
		data[9]  = 0.0f;
		data[10] = diagonal;
		data[11] = 0.0f;
		data[12] = 0.0f;
		data[13] = 0.0f;
		data[14] = 0.0f;
		data[15] = diagonal;
	}
	public static mat4x4 identity() {
		return new mat4x4(1.0f);
	}
	public mat4x4 multiply(final mat4x4 m2) {
		/* 
		 * Really hope java does some form of simd acceleration, or uses the dot product instruction.
		 * May have to use a externally compiled C library to get any decent performance.
		 */
		mat4x4 m1 = this;
		float res[] = new float[16];

		/* First row of the matrix. */
		res[0]  = (data[0] * m2.data[0]) + (data[1] * m2.data[4]) + (data[2] * m2.data[8]) + (data[3] * m2.data[12]);
		res[1]  = (data[0] * m2.data[1]) + (data[1] * m2.data[5]) + (data[2] * m2.data[9]) + (data[3] * m2.data[13]);
		res[2]  = (data[0] * m2.data[2]) + (data[1] * m2.data[6]) + (data[2] * m2.data[10]) + (data[3] * m2.data[14]);
		res[3]  = (data[0] * m2.data[3]) + (data[1] * m2.data[7]) + (data[2] * m2.data[11]) + (data[3] * m2.data[15]);

		/* Second row of the matrix. */
		res[4]  = (data[4] * m2.data[0]) + (data[5] * m2.data[4]) + (data[6] * m2.data[8]) + (data[7] * m2.data[12]);
		res[5]  = (data[4] * m2.data[1]) + (data[5] * m2.data[5]) + (data[6] * m2.data[9]) + (data[7] * m2.data[13]);
		res[6]  = (data[4] * m2.data[2]) + (data[5] * m2.data[6]) + (data[6] * m2.data[10]) + (data[7] * m2.data[14]);
		res[7]  = (data[4] * m2.data[3]) + (data[5] * m2.data[7]) + (data[6] * m2.data[11]) + (data[7] * m2.data[15]);

		/* Third row of the matrix. */
		res[8]  = data[8] * m2.data[0] + data[9] * m2.data[4] + data[10] * m2.data[8] + data[11] * m2.data[12];
		res[9]  = data[8] * m2.data[1] + data[9] * m2.data[5] + data[10] * m2.data[9] + data[11] * m2.data[13];
		res[10] = data[8] * m2.data[2] + data[9] * m2.data[6] + data[10] * m2.data[10] + data[11] * m2.data[14];
		res[11] = data[8] * m2.data[3] + data[9] * m2.data[7] + data[10] * m2.data[11] + data[11] * m2.data[15];

		/* Final row of the matrix. */
		res[12] = data[12] * m2.data[0] + data[13] * m2.data[4] + data[14] * m2.data[8] + data[15] * m2.data[12];
		res[13] = data[12] * m2.data[1] + data[13] * m2.data[5] + data[14] * m2.data[9] + data[15] * m2.data[13];
		res[14] = data[12] * m2.data[2] + data[13] * m2.data[6] + data[14] * m2.data[10] + data[15] * m2.data[14];
		res[15] = data[12] * m2.data[3] + data[13] * m2.data[7] + data[14] * m2.data[11] + data[15] * m2.data[15];
		data = res;
		return this;
	}
	public mat4x4 orthographic(final float left, final float right, final float bottom, final float top, final float far, final float near) {
		data[0]  = 2 / (right - left);
		data[3]  = -((right + left) / (right - left));
		data[5]  = 2 / (top - bottom);
		data[7]  = -((top + bottom) / (right - left));
		data[10] = -2 / (far - near);
		data[11] = -((far + near) / (far - near));
		data[15] = 1;
		return this;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		final mat4x4 cast = (mat4x4)obj;
		return Arrays.equals(data, cast.data);
	}
}
