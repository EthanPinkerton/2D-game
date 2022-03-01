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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import pinkerton.ethan.maths.*;

public final class matrix {

	@Test
	public void mat4x4_multiply_test() {
		/* Use my algorithm to multiply the matrix's together. */
		mat4x4 m1 = new mat4x4(3.0f);
		mat4x4 m2 = new mat4x4();
		Arrays.fill(m2.data, 3.0f);
		m1 = m1.multiply(m2);

		/* Check against a precomputed answer. */
		float constants[] = new float[16];
		Arrays.fill(constants, 9.0f);
		assertArrayEquals(m1.data, constants);
	}
}
