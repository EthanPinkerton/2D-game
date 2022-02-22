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

package pinkerton.ethan.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;


public class file_handler {
    private File handle;

    private boolean validate_read_mode() {
        /* Validates that reading the file is valid, it exists and can be read from. */
        return this.handle.canRead() && this.handle.exists();
    }
    private boolean validate_write_mode() {
        if (!this.handle.exists()) {
            try {
                this.handle.createNewFile();
            } catch (Exception e) {
                System.out.printf("error: cannot create %s for writing, exception thrown, %s.\n");
                return false;
            }
        }
        return this.handle.canWrite();
    }

    public static file_handler create(final String path, final boolean read_mode) {
        file_handler result = new file_handler();
        result.handle = new File(path);
        /* Depending on the whether it is read or write mode, it validates it can and then returns the result or null depending on the result. */
        return (read_mode) ? ((result.validate_read_mode()) ? result : null) : ((result.validate_write_mode()) ? result : null);
    }
    public String read() {
        FileInputStream input_stream;
        byte buffer[];
        try {
            input_stream = new FileInputStream(this.handle);
            buffer = new byte[((int)this.handle.length())];
            input_stream.read(buffer);
            input_stream.close();
        } catch (Exception e) {
            System.out.printf("error: failed to read %s, exception raised, %s.\n", this.handle.getName(), e.toString());
            return null;
        }
        /* Cast our bytes to a string, using ascii. */
        try {
            return new String(buffer, StandardCharsets.US_ASCII);
        } catch (Exception e) {
            System.out.printf("error: unable to cast read bytes from %s to US-ASCII, check file encoding.");
            return null;
        }
    }
}
