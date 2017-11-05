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
import java.nio.IntBuffer;
import java.util.Collection;
import java.util.PrimitiveIterator;

/**
 * Created by scr on 7/2/15.
 */
public class IntBufferCollection implements IndexedCollection<Integer> {
    @Nonnull
    private final IntBuffer BUFFER;

    @Nullable
    @Override
    public Integer get(int index) {
        return BUFFER.get(index);
    }

    @Override
    public void writeOutput(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(Constants.MAGIC);
        dataOutput.writeInt(Constants.VERSION);
        int size = BUFFER.remaining();
        dataOutput.writeInt(size);
        for (int i = 0; i < size; ++i) {
            dataOutput.writeInt(BUFFER.get(i));
        }
    }

    public static class BufferIterator implements PrimitiveIterator.OfInt {
        @Nonnull
        private final IntBuffer BUFFER;

        public BufferIterator(IntBuffer buffer) {
            BUFFER = buffer.duplicate();
        }

        @Override
        public boolean hasNext() {
            return BUFFER.hasRemaining();
        }

        @Nonnull
        @Override
        public Integer next() {
            return nextInt();
        }

        @Override
        public int nextInt() {
            return BUFFER.get();
        }
    }

    public IntBufferCollection(IntBuffer ints) {
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
        if (!(o instanceof Integer)) {
            return false;
        }
        Integer oInteger = (Integer) o;
        return Iterators.contains(iterator(), oInteger);
    }

    @Nonnull
    @Override
    public PrimitiveIterator.OfInt iterator() {
        return new BufferIterator(BUFFER);
    }

    @Nonnull
    public int[] toIntArray() {
        int size = size();
        int[] ret = new int[size];
        for (int i = 0; i < size; i++) {
            ret[i] = BUFFER.get(i);
        }
        return ret;
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return Iterables.toArray(this, Integer.class);
    }

    @Nonnull
    @Override
    public <T> T[] toArray(T[] a) {
        int size = size();
        //noinspection unchecked
        a = a.length >= size ? a : (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        for (int i = 0; i < size; ++i) {
            Array.setInt(a, i, BUFFER.get(i));
        }
        return a;
    }

    @Override
    public boolean add(Integer integer) {
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
    public boolean addAll(Collection<? extends Integer> c) {
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
    public int getInt(int index) {
        return BUFFER.get(index);
    }
}
