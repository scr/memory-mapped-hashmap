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

import com.github.scr.hashmap.Constants;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ShortBuffer;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by scr on 7/2/15.
 */
public class ShortBufferCollection implements IndexedCollection<Short> {
    @Nonnull
    private final ShortBuffer BUFFER;

    @Nullable
    @Override
    public Short get(int index) {
        return BUFFER.get(index);
    }

    @Override
    public void writeOutput(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(Constants.MAGIC);
        dataOutput.writeInt(Constants.VERSION);
        int size = BUFFER.remaining();
        dataOutput.writeInt(size);
        for (int i = 0; i < size; ++i) {
            dataOutput.writeShort(BUFFER.get(i));
        }
    }

    public static class BufferIterator implements Iterator<Short> {
        @Nonnull
        private final ShortBuffer BUFFER;

        public BufferIterator(ShortBuffer buffer) {
            BUFFER = buffer.duplicate();
        }

        @Override
        public boolean hasNext() {
            return BUFFER.hasRemaining();
        }

        @Nonnull
        @Override
        public Short next() {
            return BUFFER.get();
        }
    }

    public ShortBufferCollection(ShortBuffer ints) {
        BUFFER = ints;
    }

    @Override
    public int size() {
        return BUFFER.capacity();
    }

    @Override
    public boolean isEmpty() {
        return BUFFER.capacity() > 0;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Short)) {
            return false;
        }
        Short oShort = (Short) o;
        return Iterators.contains(iterator(), oShort);
    }

    @Nonnull
    @Override
    public Iterator<Short> iterator() {
        return new BufferIterator(BUFFER);
    }

    @Nonnull
    public short[] toShortArray() {
        int size = size();
        short[] ret = new short[size];
        for (int i = 0; i < size; i++) {
            ret[i] = BUFFER.get(i);
        }
        return ret;
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return Iterables.toArray(this, Short.class);
    }

    @Nonnull
    @Override
    public <T> T[] toArray(T[] a) {
        int size = size();
        //noinspection unchecked
        a = a.length >= size ? a : (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        for (int i = 0; i < size; ++i) {
            Array.setShort(a, i, BUFFER.get(i));
        }
        return a;
    }

    @Override
    public boolean add(Short aShort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Short> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getShort(int index) {
        return BUFFER.get(index);
    }
}
