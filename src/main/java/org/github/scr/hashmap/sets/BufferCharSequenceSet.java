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

package org.github.scr.hashmap.sets;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import org.github.scr.hashmap.utils.CharSequenceIterator;
import org.github.scr.hashmap.utils.CharSequences;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.github.scr.hashmap.Constants.MAGIC;
import static org.github.scr.hashmap.Constants.VERSION;

/**
 * Created by scr on 7/3/15.
 */
public class BufferCharSequenceSet implements IndexedSet<CharSequence> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BufferCharSequenceSet.class);
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private final int SIZE;
    private final int NUM_BUCKETS;
    private final IntBuffer BUCKET_OFFSETS; // start id, end id, first char offset
    private final CharBuffer ELEMENTS; // char length + char[length] characters

    public BufferCharSequenceSet(@NotNull Set<? extends CharSequence> set) {
        this(set, DEFAULT_LOAD_FACTOR);
    }

    public BufferCharSequenceSet(@NotNull Set<? extends CharSequence> set, float loadFactor) {
        Objects.requireNonNull(set);
        if (loadFactor < 0f || loadFactor > 1f) {
            throw new IllegalArgumentException("loadFactor must be between 0 and 1");
        }

        SIZE = set.size();
        NUM_BUCKETS = (int) (SIZE / loadFactor) + 1;
        BUCKET_OFFSETS = IntBuffer.allocate(NUM_BUCKETS * 3);

        // Make Buckets
        AtomicInteger totalCharacters = new AtomicInteger(0);
        Multimap<Integer, CharSequence> buckets = HashMultimap.create();
        set.forEach(e -> {
            totalCharacters.addAndGet(e.length());
            int bucket = hashBucket(NUM_BUCKETS, e);
            buckets.put(bucket, e);
        });

        ELEMENTS = CharBuffer.allocate(totalCharacters.get() + (Character.BYTES * SIZE));

        int id = 0;
        int charOffset = 0;
        for (int i = 0; i < NUM_BUCKETS; ++i) {
            if (!buckets.containsKey(i)) {
                continue;
            }
            Collection<CharSequence> elements = buckets.get(i);
            int offsetIndex = i * 3;
            BUCKET_OFFSETS.put(offsetIndex++, id);
            BUCKET_OFFSETS.put(offsetIndex++, id += elements.size());
            BUCKET_OFFSETS.put(offsetIndex, charOffset);
            for (CharSequence element : elements) {
                int elementLength = element.length();
                if (elementLength > Character.MAX_VALUE) {
                    throw new IllegalArgumentException("Size of string is too large to be described in a char.");
                }
                ELEMENTS.put(charOffset++, (char) elementLength);
                for (int j = 0; j < elementLength; ++j) {
                    ELEMENTS.put(charOffset++, element.charAt(j));
                }
            }
        }
        LOGGER.debug("Finished initializing");
    }

    public BufferCharSequenceSet(@NotNull ByteBuffer byteBuffer) {
        int magic = byteBuffer.getInt();
        if (magic != MAGIC) {
            throw new IllegalArgumentException("bad magic number");
        }
        int version = byteBuffer.getInt();
        if (version != VERSION) {
            throw new IllegalArgumentException("bad version number");
        }
        SIZE = byteBuffer.getInt();
        NUM_BUCKETS = byteBuffer.getInt();

        // Set up the offsets.
        IntBuffer offsets = byteBuffer.asIntBuffer();
        offsets.limit(NUM_BUCKETS * 3);
        BUCKET_OFFSETS = offsets.slice();
        byteBuffer.position(byteBuffer.position() + NUM_BUCKETS * 3 * Integer.BYTES);

        // Set up the elements.
        int elementCapacity = byteBuffer.getInt();
        CharBuffer elements = byteBuffer.asCharBuffer();
        elements.limit(elementCapacity);
        ELEMENTS = elements.slice();
        byteBuffer.position(byteBuffer.position() + elementCapacity * Character.BYTES);
    }

    public static BufferCharSequenceSet of(@NotNull Path file) throws IOException {
        try (FileChannel fc = FileChannel.open(file)) {
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            return new BufferCharSequenceSet(byteBuffer);
        }
    }

    @Override
    public void writeOutput(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(MAGIC);  // Magic
        dataOutput.writeInt(VERSION);  // Version

        dataOutput.writeInt(SIZE);
        dataOutput.writeInt(NUM_BUCKETS);
        // Write out the offsets.
        for (int i = 0; i < NUM_BUCKETS * 3; ) {
            dataOutput.writeInt(BUCKET_OFFSETS.get(i++));
            dataOutput.writeInt(BUCKET_OFFSETS.get(i++));
            dataOutput.writeInt(BUCKET_OFFSETS.get(i++));
        }
        // Write out the elements.
        int elementCapacity = ELEMENTS.capacity();
        dataOutput.writeInt(elementCapacity);
        for (int i = 0; i < elementCapacity; ++i) {
            dataOutput.writeChar(ELEMENTS.get(i));
        }
    }

    static int hash(CharSequence key) {
        int h;
        return (key == null) ? 0 : (h = CharSequences.hashCode(key)) ^ (h >>> 16);
    }

    static int hashBucket(int numBuckets, CharSequence key) {
        return (numBuckets - 1) & hash(key);
    }

    @Override
    public int getIndex(@NotNull CharSequence element) {
        {
            int bucket = hashBucket(NUM_BUCKETS, element);
            int offsetIndex = bucket * 3;
            int idStart = BUCKET_OFFSETS.get(offsetIndex++);
            int idEnd = BUCKET_OFFSETS.get(offsetIndex++);
            int charOffset = BUCKET_OFFSETS.get(offsetIndex);

            for (int i = idStart; i < idEnd; ++i) {
                char length = ELEMENTS.get(charOffset++);
                if (CharSequences.equalTo(element, ELEMENTS, charOffset, length)) {
                    return i;
                }
                charOffset += length;
            }
            return -1;
        }
    }

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public boolean isEmpty() {
        return SIZE <= 0;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof CharSequence)) {
            LOGGER.error("Element of type {} is not a CharSequence", o.getClass().getCanonicalName());
            throw new IllegalArgumentException("Element is not a CharSequence");
        }
        return getIndex((CharSequence) o) >= 0;
    }

    @NotNull
    @Override
    public Iterator<CharSequence> iterator() {
        return new CharSequenceIterator(ELEMENTS);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Object[] ret = new Object[SIZE];
        int index = 0;
        for (CharSequence charSequence : this) {
            ret[index++] = charSequence;
        }
        return ret;
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        if (a.length < SIZE) {
            a = Arrays.copyOf(a, SIZE);
        }
        int index = 0;
        for (CharSequence charSequence : this) {
            @SuppressWarnings("unchecked")
            T t = (T) charSequence;
            a[index++] = t;
        }
        return a;
    }

    @Override
    public boolean add(CharSequence charSequence) {
        throw new NotImplementedException();
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return Iterables.all(c, this::contains);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends CharSequence> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }
}
