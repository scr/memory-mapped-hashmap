package com.github.scr.hashmap.function;

import java.util.Objects;
import java.util.PrimitiveIterator;

public interface PrimitiveShortIterator extends PrimitiveIterator<Short, ShortConsumer> {
    @Override
    default Short next() {
        return nextShort();
    }

    short nextShort();

    @Override
    default void forEachRemaining(ShortConsumer action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(nextShort());
    }
}
