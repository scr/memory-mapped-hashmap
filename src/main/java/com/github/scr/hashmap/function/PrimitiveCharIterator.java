package com.github.scr.hashmap.function;

import java.util.Objects;
import java.util.PrimitiveIterator;

public interface PrimitiveCharIterator extends PrimitiveIterator<Character, CharConsumer> {
    @Override
    default Character next() {
        return nextChar();
    }

    char nextChar();

    @Override
    default void forEachRemaining(CharConsumer action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(nextChar());
    }
}
