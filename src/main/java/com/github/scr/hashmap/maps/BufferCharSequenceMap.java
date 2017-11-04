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

import com.github.scr.hashmap.collections.IndexedCollection;
import com.github.scr.hashmap.sets.IndexedSet;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Nonnull;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by scr on 7/3/15.
 */
public class BufferCharSequenceMap<V> implements IndexedMap<CharSequence, V> {
    @Nonnull
    protected final IndexedSet<CharSequence> KEYS;
    @Nonnull
    protected final IndexedCollection<V> VALUES;

    public BufferCharSequenceMap(IndexedSet<CharSequence> keys, IndexedCollection<V> values) {
        KEYS = keys;
        VALUES = values;
    }

    @Override
    public void writeOutput(DataOutput dataOutput) throws IOException {
        KEYS.writeOutput(dataOutput);
        VALUES.writeOutput(dataOutput);
    }

    @Override
    public int size() {
        return KEYS.size();
    }

    @Override
    public boolean isEmpty() {
        return KEYS.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return KEYS.contains((CharSequence) key);
    }

    @Override
    public boolean containsValue(Object value) {
        @SuppressWarnings("unchecked")
        V vValue = (V) value;
        return VALUES.contains(vValue);
    }

    @Override
    public V get(Object key) {
        int index = KEYS.getIndex((CharSequence) key);
        if (index < 0) {
            return null;
        }
        return VALUES.get(index);
    }

    @Override
    public V put(CharSequence key, V value) {
        throw new NotImplementedException();
    }

    @Override
    public V remove(Object key) {
        throw new NotImplementedException();
    }

    @Override
    public void putAll(Map<? extends CharSequence, ? extends V> m) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public Set<CharSequence> keySet() {
        return KEYS;
    }

    @Nonnull
    @Override
    public Collection<V> values() {
        return VALUES;
    }

    @Nonnull
    @Override
    public Set<Entry<CharSequence, V>> entrySet() {
        return new EntrySet<>(KEYS, VALUES);
    }

    @Override
    public byte getByte(CharSequence key) throws NoSuchElementException {
        int index = KEYS.getIndex(key);
        if (index < 0) {
            throw new NoSuchElementException();
        }
        return VALUES.getByte(index);
    }

    @Override
    public char getChar(CharSequence key) throws NoSuchElementException {
        int index = KEYS.getIndex(key);
        if (index < 0) {
            throw new NoSuchElementException();
        }
        return VALUES.getChar(index);
    }

    @Override
    public double getDouble(CharSequence key) throws NoSuchElementException {
        int index = KEYS.getIndex(key);
        if (index < 0) {
            throw new NoSuchElementException();
        }
        return VALUES.getDouble(index);
    }

    @Override
    public float getFloat(CharSequence key) throws NoSuchElementException {
        int index = KEYS.getIndex(key);
        if (index < 0) {
            throw new NoSuchElementException();
        }
        return VALUES.getFloat(index);
    }

    @Override
    public long getLong(CharSequence key) throws NoSuchElementException {
        int index = KEYS.getIndex(key);
        if (index < 0) {
            throw new NoSuchElementException();
        }
        return VALUES.getLong(index);
    }

    @Override
    public short getShort(CharSequence key) throws NoSuchElementException {
        int index = KEYS.getIndex(key);
        if (index < 0) {
            throw new NoSuchElementException();
        }
        return VALUES.getShort(index);
    }

    @Override
    public int getInt(CharSequence key) throws NoSuchElementException {
        int index = KEYS.getIndex(key);
        if (index < 0) {
            throw new NoSuchElementException();
        }
        return VALUES.getInt(index);
    }
}
