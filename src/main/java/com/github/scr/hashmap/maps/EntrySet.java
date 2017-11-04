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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by scr on 7/3/15.
 */
public class EntrySet<V> implements Set<Map.Entry<CharSequence, V>> {
    private final Set<CharSequence> KEYS;
    private final IndexedCollection<V> VALUES;

    public EntrySet(Set<CharSequence> keys, IndexedCollection<V> values) {
        KEYS = keys;
        VALUES = values;
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
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        Map.Entry<CharSequence, V> oEntry = (Map.Entry<CharSequence, V>) o;
        return KEYS.contains(oEntry.getKey()) && VALUES.contains(oEntry.getValue());
    }

    @Nonnull
    @Override
    public Iterator<Map.Entry<CharSequence, V>> iterator() {
        return new EntryIterator<>(KEYS.iterator(), VALUES.iterator());
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        Object[] ret = new Object[KEYS.size()];
        int i = 0;
        for (Map.Entry<CharSequence, V> charSequenceIntegerEntry : this) {
            ret[i++] = charSequenceIntegerEntry;
        }
        return ret;
    }

    @Nonnull
    @Override
    public <T> T[] toArray(T[] a) {
        {
            if (a.length < KEYS.size()) {
                a = Arrays.copyOf(a, KEYS.size());
            }
            int i = 0;
            for (Map.Entry<CharSequence, V> entry : this) {
                @SuppressWarnings("unchecked")
                T tEntry = (T) entry;
                a[i++] = tEntry;
            }
            return a;
        }
    }

    @Override
    public boolean add(Map.Entry<CharSequence, V> charSequenceVEntry) {
        throw new NotImplementedException();
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
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
    public boolean addAll(Collection<? extends Map.Entry<CharSequence, V>> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }
}
