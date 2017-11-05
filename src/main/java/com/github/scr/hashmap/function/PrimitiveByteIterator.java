package com.github.scr.hashmap.function;

import java.util.Objects;
import java.util.PrimitiveIterator;

public interface PrimitiveByteIterator extends PrimitiveIterator<Byte, ByteConsumer> {
    @Override
    default Byte next() {
        return nextByte();
    }

    byte nextByte();

    @Override
    default void forEachRemaining(ByteConsumer action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(nextByte());
    }
}
