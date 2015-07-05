package org.github.scr.hashmap;

import org.github.scr.hashmap.sets.BufferCharSequenceSet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}