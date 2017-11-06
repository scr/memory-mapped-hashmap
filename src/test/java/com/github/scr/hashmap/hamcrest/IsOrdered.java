package com.github.scr.hashmap.hamcrest;

import com.google.common.collect.Ordering;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;

public class IsOrdered<T extends Iterable<E>, E> extends CustomTypeSafeMatcher<T> {
    private final Ordering<E> ORDERING;

    public IsOrdered(String description, Ordering<E> ordering) {
        super(description);
        this.ORDERING = ordering;
    }

    @Override
    protected boolean matchesSafely(T t) {
        return ORDERING.isOrdered(t);
    }

    public static <T extends Iterable<E>, E extends Comparable<E>> Matcher<T> naturallyOrdered() {
        return new IsOrdered<>("A naturally ordered iterable", Ordering.natural());
    }
}
