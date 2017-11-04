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

import com.google.common.collect.Iterators;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static com.github.scr.hashmap.Constants.MAGIC;
import static com.github.scr.hashmap.Constants.VERSION;

/**
 * Created by scr on 7/2/15.
 */
public class CharacterBufferCollection implements IndexedCollection<Character> {
    @Nonnull
    private final CharBuffer BUFFER;

    @Nullable
    @Override
    public Character get(int index) {
        return BUFFER.get(index);
    }

    @Override
    public void writeOutput(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(MAGIC);
        dataOutput.writeInt(VERSION);
        int size = BUFFER.remaining();
        dataOutput.writeInt(size);
        for (int i = 0; i < size; ++i) {
            dataOutput.writeChar(BUFFER.get(i));
        }
    }

    public static class BufferIterator implements Iterator<Character> {
        @Nonnull
        private final CharBuffer BUFFER;

        public BufferIterator(CharBuffer buffer) {
            BUFFER = buffer.duplicate();
        }

        @Override
        public boolean hasNext() {
            return BUFFER.hasRemaining();
        }

        @Nonnull
        @Override
        public Character next() {
            return BUFFER.get();
        }
    }

    public CharacterBufferCollection(CharBuffer ints) {
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
        if (!(o instanceof Character)) {
            return false;
        }
        Character oCharacter = (Character) o;
        return Iterators.contains(iterator(), oCharacter);
    }

    @Nonnull
    @Override
    public Iterator<Character> iterator() {
        return new BufferIterator(BUFFER);
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        int size = size();
        Object[] ret = new Object[size];
        for (int i = 0; i < size; ++i) {
            ret[i] = BUFFER.get(i);
        }
        return ret;
    }

    @Nonnull
    @Override
    public <T> T[] toArray(T[] a) {
        int size = size();
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        for (int i = 0; i < size; ++i) {
            @SuppressWarnings("unchecked")
            T t = (T) Character.valueOf(BUFFER.get(i));
            a[i] = t;
        }
        return a;
    }

    @Override
    public boolean add(Character aCharacter) {
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
    public boolean addAll(Collection<? extends Character> c) {
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
    public char getChar(int index) {
        return BUFFER.get(index);
    }
}
