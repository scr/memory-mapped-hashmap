package com.github.scr.hashmap.collections;

import com.github.scr.hashmap.utils.CharSequenceIterator;
import com.github.scr.hashmap.utils.CharSequences;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static com.github.scr.hashmap.Constants.MAGIC;
import static com.github.scr.hashmap.Constants.VERSION;

/**
 * @author scr on 1/30/16.
 */
public class CharSequenceCollection implements IndexedCollection<CharSequence> {
    private final int SIZE;
    private final IntBuffer OFFSETS; // first char offset
    private final CharBuffer ELEMENTS; // char length + char[length] characters

    public CharSequenceCollection(@NotNull Collection<? extends CharSequence> elements) {
        SIZE = elements.size();
        OFFSETS = IntBuffer.allocate(SIZE);

        int totalCharacters = elements.stream().mapToInt(CharSequence::length).sum();

        int charOffset = 0;
        ELEMENTS = CharBuffer.allocate(SIZE + totalCharacters);  // SIZE lengths + all of the characters.
        for (CharSequence element : elements) {
            OFFSETS.put(charOffset);
            int elementLength = element.length();
            Preconditions.checkElementIndex(elementLength, Character.MAX_VALUE);
            ELEMENTS.append((char) elementLength);
            ELEMENTS.append(element);
            charOffset += 1 + elementLength;
        }
        OFFSETS.flip();
        ELEMENTS.flip();

        Preconditions.checkState(charOffset == ELEMENTS.capacity());
    }

    public CharSequenceCollection(@NotNull ByteBuffer byteBuffer) {
        int magic = byteBuffer.getInt();
        if (magic != MAGIC) {
            throw new IllegalArgumentException("bad magic number");
        }
        int version = byteBuffer.getInt();
        if (version != VERSION) {
            throw new IllegalArgumentException("bad version number");
        }

        SIZE = byteBuffer.getInt();
        IntBuffer offsets = byteBuffer.asIntBuffer();
        offsets.limit(SIZE);
        OFFSETS = offsets.slice();
        byteBuffer.position(byteBuffer.position() + Integer.BYTES * SIZE);

        int elementCapacity = byteBuffer.getInt();
        CharBuffer elements = byteBuffer.asCharBuffer();
        elements.limit(elementCapacity);
        ELEMENTS = elements.slice();
        byteBuffer.position(byteBuffer.position() + Character.BYTES * elementCapacity);
    }

    public static CharSequenceCollection fromPath(@NotNull Path path) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(path)) {
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            return new CharSequenceCollection(byteBuffer);
        }
    }

    @Nullable
    @Override
    public CharBuffer get(int index) {
        Preconditions.checkElementIndex(index, SIZE);
        CharBuffer ret = ELEMENTS.duplicate();
        int offset = OFFSETS.get(index);
        ret.position(offset);
        int size = Short.toUnsignedInt((short) ret.get());
        ret.limit(offset + 1 + size);
        return ret.slice();
    }

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public boolean isEmpty() {
        return SIZE == 0;
    }

    @Override
    public boolean contains(@NotNull Object o) {
        if (!(o instanceof CharSequence)) {
            return false;
        }
        CharSequence oCharSequence = (CharSequence) o;

        return Iterators.any(iterator(), it -> CharSequences.equalTo(it, oCharSequence));
    }

    @NotNull
    @Override
    public Iterator<CharSequence> iterator() {
        return new CharSequenceIterator(ELEMENTS);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return Iterators.toArray(iterator(), CharSequence.class);
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        int size = size();
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        Iterator<CharSequence> iterator = iterator();
        for (int i = 0; i < size; ++i) {
            @SuppressWarnings("unchecked")
            T t = (T) iterator.next();
            a[i] = t;
        }
        return a;
    }

    @Override
    public boolean add(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(@NotNull Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends CharSequence> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeOutput(@NotNull DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(MAGIC);
        dataOutput.writeInt(VERSION);

        dataOutput.writeInt(SIZE);
        for (int i = 0; i < SIZE; i++) {
            dataOutput.writeInt(OFFSETS.get(i));
        }

        int elementCapacity = ELEMENTS.capacity();
        dataOutput.writeInt(elementCapacity);
        for (int i = 0; i < elementCapacity; i++) {
            dataOutput.writeChar(ELEMENTS.get(i));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Iterable)) return false;

        //noinspection unchecked
        return CharSequences.allEqualTo(this, (Iterable<CharSequence>) o);
    }

    @Override
    public int hashCode() {
        int result = SIZE;
        for (CharSequence charSequence : this) {
            result = 31 * result + CharSequences.hashCode(charSequence);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (CharSequence charSequence : this) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(charSequence.toString());
        }
        sb.append(']');
        return sb.toString();
    }
}
