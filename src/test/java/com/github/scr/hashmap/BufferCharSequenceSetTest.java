package com.github.scr.hashmap;

import com.github.scr.hashmap.sets.BufferCharSequenceSet;
import com.google.common.collect.Iterators;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 7/3/15.
 */
@Test
public class BufferCharSequenceSetTest {
    @DataProvider(name = "sets")
    public static Object[][] sets() {
        List<String> elements = Arrays.asList("one", "two", "three", "four", "five");
        return new Object[][]{
                {
                        new HashSet<>(elements),
                        elements,
                },
        };
    }

    @Test(dataProvider = "sets")
    public void testBufferedSet(Set<String> input, List<String> elements) throws Exception {
        BufferCharSequenceSet bcsSet = new BufferCharSequenceSet(input);
        assertThat("Has all elements", bcsSet.containsAll(elements));
    }

    @Test(dataProvider = "sets")
    public void testBufferedSetHasIteratorOfSameSize(Set<String> input, List<String> elements) throws Exception {
        BufferCharSequenceSet bcsSet = new BufferCharSequenceSet(input);
        assertThat(Iterators.size(bcsSet.iterator()), is(elements.size()));
    }

    @Test(enabled = false, description = "indices are unfortunately in hash sequence order no matter the input order")
    public void testSequencedSetIsSameOrder() throws Exception {
        List<String> list = Arrays.asList("foo", "bar", "baz", "biz", "buz");
        Set<String> set = new LinkedHashSet<>(list);
        BufferCharSequenceSet bcsSet = new BufferCharSequenceSet(set);
        for (int i = 0; i < list.size(); i++) {
            String word = list.get(i);
            assertThat(word + " should have index " + i, bcsSet.getIndex(list.get(i)), is(i));
        }
    }
}