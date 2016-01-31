package com.github.scr.hashmap.collections;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 1/30/16.
 */
@Test
public class CharSequenceCollectionTest {
    @Test
    public void testCharSequence() throws Exception {
        List<String> expected = ImmutableList.of("foo", "bar", "baz");
        Collection<CharSequence> charSequences = new CharSequenceCollection(expected);
        assertThat(charSequences, is(expected));
        assertThat("doesn't contain \"foo\"", charSequences.contains("foo"));
        assertThat("doesn't containAll", charSequences.containsAll(expected));
    }
}