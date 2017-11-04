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

package com.github.scr.hashmap.maps;

import com.github.scr.hashmap.collections.*;
import com.github.scr.hashmap.sets.BufferCharSequenceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Map;

/**
 * Created by scr on 7/3/15.
 */
public class BufferCharSequenceMaps {
    private static final Logger LOGGER = LoggerFactory.getLogger(BufferCharSequenceMaps.class);

    // Integer
    public static BufferCharSequenceMap<Integer> intMap(Path filePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(filePath)) {
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            return intMap(byteBuffer);
        }
    }

    public static BufferCharSequenceMap<Integer> intMap(ByteBuffer byteBuffer) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(byteBuffer);
        IndexedCollection<Integer> vals = BufferCollections.ofInt(byteBuffer);
        return new BufferCharSequenceMap<>(keys, vals);
    }

    public static <K extends CharSequence> BufferCharSequenceMap<Integer> intMap(Map<K, Integer> map) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(map.keySet());
        int numKeys = keys.size();
        IntBuffer valBuffer = IntBuffer.allocate(numKeys);
        map.forEach((k, v) -> {
            try {
                valBuffer.put(keys.getIndex(k), v);
            } catch (Exception e) {
                LOGGER.error("Error getting index for {} -> {}", k, v, e);
                keys.getIndex(k);
            }
        });
        return new BufferCharSequenceMap<>(keys, new IntBufferCollection(valBuffer));
    }

    // Float
    public static BufferCharSequenceMap<Float> floatMap(Path filePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(filePath)) {
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            return floatMap(byteBuffer);
        }
    }

    public static BufferCharSequenceMap<Float> floatMap(ByteBuffer byteBuffer) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(byteBuffer);
        IndexedCollection<Float> vals = BufferCollections.ofFloat(byteBuffer);
        return new BufferCharSequenceMap<>(keys, vals);
    }

    public static BufferCharSequenceMap<Float> floatMap(Map<? extends CharSequence, Float> map) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(map.keySet());
        FloatBuffer valBuffer = FloatBuffer.allocate(keys.size());
        map.forEach((k, v) -> {
            valBuffer.put(keys.getIndex(k), v);
        });
        return new BufferCharSequenceMap<>(keys, new FloatBufferCollection(valBuffer));
    }

    // Double
    public static BufferCharSequenceMap<Double> doubleMap(Path filePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(filePath)) {
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            return doubleMap(byteBuffer);
        }
    }

    public static BufferCharSequenceMap<Double> doubleMap(ByteBuffer byteBuffer) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(byteBuffer);
        IndexedCollection<Double> vals = BufferCollections.ofDouble(byteBuffer);
        return new BufferCharSequenceMap<>(keys, vals);
    }

    public static BufferCharSequenceMap<Double> doubleMap(Map<? extends CharSequence, Double> map) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(map.keySet());
        DoubleBuffer valBuffer = DoubleBuffer.allocate(keys.size());
        map.forEach((k, v) -> {
            valBuffer.put(keys.getIndex(k), v);
        });
        return new BufferCharSequenceMap<>(keys, new DoubleBufferCollection(valBuffer));
    }

    // Long
    public static BufferCharSequenceMap<Long> longMap(Path filePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(filePath)) {
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            return longMap(byteBuffer);
        }
    }

    public static BufferCharSequenceMap<Long> longMap(ByteBuffer byteBuffer) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(byteBuffer);
        IndexedCollection<Long> vals = BufferCollections.ofLong(byteBuffer);
        return new BufferCharSequenceMap<>(keys, vals);
    }

    public static BufferCharSequenceMap<Long> longMap(Map<? extends CharSequence, Long> map) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(map.keySet());
        LongBuffer valBuffer = LongBuffer.allocate(keys.size());
        map.forEach((k, v) -> {
            valBuffer.put(keys.getIndex(k), v);
        });
        return new BufferCharSequenceMap<>(keys, new LongBufferCollection(valBuffer));
    }

    // Short
    public static BufferCharSequenceMap<Short> shortMap(Path filePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(filePath)) {
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            return shortMap(byteBuffer);
        }
    }

    public static BufferCharSequenceMap<Short> shortMap(ByteBuffer byteBuffer) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(byteBuffer);
        IndexedCollection<Short> vals = BufferCollections.ofShort(byteBuffer);
        return new BufferCharSequenceMap<>(keys, vals);
    }

    public static BufferCharSequenceMap<Short> shortMap(Map<? extends CharSequence, Short> map) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(map.keySet());
        ShortBuffer valBuffer = ShortBuffer.allocate(keys.size());
        map.forEach((k, v) -> {
            valBuffer.put(keys.getIndex(k), v);
        });
        return new BufferCharSequenceMap<>(keys, new ShortBufferCollection(valBuffer));
    }

    // Byte
    public static BufferCharSequenceMap<Byte> byteMap(Path filePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(filePath)) {
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            return byteMap(byteBuffer);
        }
    }

    public static BufferCharSequenceMap<Byte> byteMap(ByteBuffer byteBuffer) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(byteBuffer);
        IndexedCollection<Byte> vals = BufferCollections.ofByte(byteBuffer);
        return new BufferCharSequenceMap<>(keys, vals);
    }

    public static BufferCharSequenceMap<Byte> byteMap(Map<? extends CharSequence, Byte> map) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(map.keySet());
        ByteBuffer valBuffer = ByteBuffer.allocate(keys.size());
        map.forEach((k, v) -> {
            valBuffer.put(keys.getIndex(k), v);
        });
        return new BufferCharSequenceMap<>(keys, new ByteBufferCollection(valBuffer));
    }

    // Character
    public static BufferCharSequenceMap<Character> characterMap(Path filePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(filePath)) {
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            return characterMap(byteBuffer);
        }
    }

    public static BufferCharSequenceMap<Character> characterMap(ByteBuffer byteBuffer) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(byteBuffer);
        IndexedCollection<Character> vals = BufferCollections.ofCharacter(byteBuffer);
        return new BufferCharSequenceMap<>(keys, vals);
    }

    public static BufferCharSequenceMap<Character> characterMap(Map<? extends CharSequence, Character> map) {
        BufferCharSequenceSet keys = new BufferCharSequenceSet(map.keySet());
        CharBuffer valBuffer = CharBuffer.allocate(keys.size());
        map.forEach((k, v) -> {
            valBuffer.put(keys.getIndex(k), v);
        });
        return new BufferCharSequenceMap<>(keys, new CharacterBufferCollection(valBuffer));
    }
}
