package com.github.scr.hashmap.function;

import java.util.Objects;
import java.util.PrimitiveIterator;

public interface PrimitiveFloatIterator extends PrimitiveIterator<Float, FloatConsumer> {
    @Override
    default Float next() {
        return nextFloat();
    }

    float nextFloat();

    @Override
    default void forEachRemaining(FloatConsumer action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(nextFloat());
    }
}
