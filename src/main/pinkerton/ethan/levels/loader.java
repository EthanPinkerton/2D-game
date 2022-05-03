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

import org.joml.Vector2f;
import pinkerton.ethan.graphics.texture;
import pinkerton.ethan.util.file_handler;
import org.joml.Vector4f;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public final class loader {

    public String path;
    public int rows;
    public int columns;
    public int iterator;
    private int internal_iterator;
    public String data;

    public loader(final String path) {
        this.path     = path;
        this.rows     = 0;
        this.columns  = 0;
        this.data     = null;
        this.internal_iterator = 0;
    }
    public boolean load() {
        file_handler handle;
        if ((handle = file_handler.create(path, true)) == null) {
            System.out.printf("error: failed to use file handler on %s.\n", path);
            return false;
        }
        if ((data = handle.read()) == null) {
            System.out.printf("error: failed to read %s's data.\n", path);
            return false;
        }
        /* Use the read data to do some interrogation of the data about the level, makes the assumption that it is either square or rectangular. */
        String lines[] = data.split("\n");
        rows    = lines.length;
        columns = lines[0].length();
        System.out.printf("info:  loaded %s, map dimensions %dx%d.\n", path, columns, rows);
        data = data.replaceAll(System.getProperty("line.separator"), "");
        return true;
    }
    public static Vector4f construct(final char tile, final int x, final int y) {
        int texture_index;
        /* Empty tile. */
        if ((texture_index = tile_index(tile)) == -1)
            return null;
        Vector2f offsets = texture.calculate_offsets(6, texture_index);
        /* Returns the base offset and texture position of the tile. */
        return new Vector4f(2.0f * x, -2.0f * y, offsets.x, offsets.y);
    }
    public char iterate() {
        try {
            char result = data.charAt(internal_iterator);
            if (result != '\n')
                ++iterator;
            ++internal_iterator;
            return result;
        } catch (Exception e) {
            return '\0';
        }
    }
    public static int tile_index(final char tile) {
        switch (tile) {
            case 'a': return 0;
            case 'b': return 1;
            case 'c': return 2;
            case '#':  return 14;
            case '?':  return 17;
            case '\n':
            case ' ':
                return -1;
        }
        return 14;
    }
}
