/*
The MIT License (MIT)

Copyright (c) 2015 Sheridan C Rawlins

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.github.scr.hashmap.collections;

import org.jetbrains.annotations.NotNull;

import java.nio.*;
import java.util.Collection;

import static com.github.scr.hashmap.Constants.MAGIC;
import static com.github.scr.hashmap.Constants.VERSION;

/**
 * Created by scr on 7/3/15.
 */
public class BufferCollections {
    @NotNull
    public static IndexedCollection<Integer> ofInt(@NotNull Collection<Integer> intCollection) {
        IntBuffer vals = IntBuffer.allocate(intCollection.size());
        intCollection.forEach(vals::put);
        vals.flip();
        return new IntBufferCollection(vals);
    }

    @NotNull
    public static IndexedCollection<Integer> ofInt(@NotNull ByteBuffer byteBuffer) {
        int magic = byteBuffer.getInt();
        if (magic != MAGIC) {
            throw new IllegalArgumentException("bad magic number");
        }
        int version = byteBuffer.getInt();
        if (version != VERSION) {
            throw new IllegalArgumentException("bad version number");
        }

        int size = byteBuffer.getInt();
        IntBuffer values = byteBuffer.asIntBuffer();
        values.limit(size);
        byteBuffer.position(byteBuffer.position() + size * Integer.BYTES);
        return new IntBufferCollection(values.slice());
    }

    @NotNull
    public static IndexedCollection<Float> ofFloat(@NotNull Collection<Float> intCollection) {
        FloatBuffer vals = FloatBuffer.allocate(intCollection.size());
        intCollection.forEach(vals::put);
        vals.flip();
        return new FloatBufferCollection(vals);
    }

    @NotNull
    public static IndexedCollection<Float> ofFloat(@NotNull ByteBuffer byteBuffer) {
        int magic = byteBuffer.getInt();
        if (magic != MAGIC) {
            throw new IllegalArgumentException("bad magic number");
        }
        int version = byteBuffer.getInt();
        if (version != VERSION) {
            throw new IllegalArgumentException("bad version number");
        }

        int size = byteBuffer.getInt();
        FloatBuffer values = byteBuffer.asFloatBuffer();
        values.limit(size);
        byteBuffer.position(byteBuffer.position() + size * Integer.BYTES);
        return new FloatBufferCollection(values.slice());
    }

    @NotNull
    public static IndexedCollection<Double> ofDouble(@NotNull Collection<Double> intCollection) {
        DoubleBuffer vals = DoubleBuffer.allocate(intCollection.size());
        intCollection.forEach(vals::put);
        vals.flip();
        return new DoubleBufferCollection(vals);
    }

    @NotNull
    public static IndexedCollection<Double> ofDouble(@NotNull ByteBuffer byteBuffer) {
        int magic = byteBuffer.getInt();
        if (magic != MAGIC) {
            throw new IllegalArgumentException("bad magic number");
        }
        int version = byteBuffer.getInt();
        if (version != VERSION) {
            throw new IllegalArgumentException("bad version number");
        }

        int size = byteBuffer.getInt();
        DoubleBuffer values = byteBuffer.asDoubleBuffer();
        values.limit(size);
        byteBuffer.position(byteBuffer.position() + size * Integer.BYTES);
        return new DoubleBufferCollection(values.slice());
    }

    @NotNull
    public static IndexedCollection<Long> ofLong(@NotNull Collection<Long> intCollection) {
        LongBuffer vals = LongBuffer.allocate(intCollection.size());
        intCollection.forEach(vals::put);
        vals.flip();
        return new LongBufferCollection(vals);
    }

    @NotNull
    public static IndexedCollection<Long> ofLong(@NotNull ByteBuffer byteBuffer) {
        int magic = byteBuffer.getInt();
        if (magic != MAGIC) {
            throw new IllegalArgumentException("bad magic number");
        }
        int version = byteBuffer.getInt();
        if (version != VERSION) {
            throw new IllegalArgumentException("bad version number");
        }

        int size = byteBuffer.getInt();
        LongBuffer values = byteBuffer.asLongBuffer();
        values.limit(size);
        byteBuffer.position(byteBuffer.position() + size * Integer.BYTES);
        return new LongBufferCollection(values.slice());
    }

    @NotNull
    public static IndexedCollection<Short> ofShort(@NotNull Collection<Short> intCollection) {
        ShortBuffer vals = ShortBuffer.allocate(intCollection.size());
        intCollection.forEach(vals::put);
        vals.flip();
        return new ShortBufferCollection(vals);
    }

    @NotNull
    public static IndexedCollection<Short> ofShort(@NotNull ByteBuffer byteBuffer) {
        int magic = byteBuffer.getInt();
        if (magic != MAGIC) {
            throw new IllegalArgumentException("bad magic number");
        }
        int version = byteBuffer.getInt();
        if (version != VERSION) {
            throw new IllegalArgumentException("bad version number");
        }

        int size = byteBuffer.getInt();
        ShortBuffer values = byteBuffer.asShortBuffer();
        values.limit(size);
        byteBuffer.position(byteBuffer.position() + size * Integer.BYTES);
        return new ShortBufferCollection(values.slice());
    }

    @NotNull
    public static IndexedCollection<Byte> ofByte(@NotNull Collection<Byte> intCollection) {
        ByteBuffer vals = ByteBuffer.allocate(intCollection.size());
        intCollection.forEach(vals::put);
        vals.flip();
        return new ByteBufferCollection(vals);
    }

    @NotNull
    public static IndexedCollection<Byte> ofByte(@NotNull ByteBuffer byteBuffer) {
        int magic = byteBuffer.getInt();
        if (magic != MAGIC) {
            throw new IllegalArgumentException("bad magic number");
        }
        int version = byteBuffer.getInt();
        if (version != VERSION) {
            throw new IllegalArgumentException("bad version number");
        }

        int size = byteBuffer.getInt();
        ByteBuffer values = byteBuffer.duplicate();
        values.limit(size);
        byteBuffer.position(byteBuffer.position() + size * Integer.BYTES);
        return new ByteBufferCollection(values.slice());
    }

    @NotNull
    public static IndexedCollection<Character> ofCharacter(@NotNull Collection<Character> intCollection) {
        CharBuffer vals = CharBuffer.allocate(intCollection.size());
        intCollection.forEach(vals::put);
        vals.flip();
        return new CharacterBufferCollection(vals);
    }

    @NotNull
    public static IndexedCollection<Character> ofCharacter(@NotNull ByteBuffer byteBuffer) {
        int magic = byteBuffer.getInt();
        if (magic != MAGIC) {
            throw new IllegalArgumentException("bad magic number");
        }
        int version = byteBuffer.getInt();
        if (version != VERSION) {
            throw new IllegalArgumentException("bad version number");
        }

        int size = byteBuffer.getInt();
        CharBuffer values = byteBuffer.asCharBuffer();
        values.limit(size);
        byteBuffer.position(byteBuffer.position() + size * Integer.BYTES);
        return new CharacterBufferCollection(values.slice());
    }
}
