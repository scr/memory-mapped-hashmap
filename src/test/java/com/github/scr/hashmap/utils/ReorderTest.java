package com.github.scr.hashmap.utils;

import com.github.scr.hashmap.sets.BufferCharSequenceSet;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReorderTest {

    @Test
    public void testReorder() throws Exception {
        List<String> list = Arrays.asList("foo", "bar", "baz", "buz", "biz");
        Set<String> set = new LinkedHashSet<>(list);
        BufferCharSequenceSet bcsSet = new BufferCharSequenceSet(set);

        int[] indices = list.stream().mapToInt(bcsSet::getIndex).toArray();
        List<String> newList = Reorder.reorderInplace(new ArrayList<>(list), Arrays.copyOf(indices, indices.length));
        for (int i = 0; i < list.size(); i++) {
            String word = newList.get(i);
            assertThat(word + " should have index " + i, bcsSet.getIndex(newList.get(i)), is(i));
        }
    }

    @Test
    public void testReorderInPlaceEqualsCopy() throws Exception {
        List<String> list = Arrays.asList("one", "two", "three");
        int[] indices = {2, 0, 1};
        List<String> copy = Reorder.reorderCopy(list, indices);
        List<String> inPlace = Reorder.reorderInplace(new ArrayList<>(list), Arrays.copyOf(indices, indices.length));
        assertThat(copy, is(Arrays.asList("two", "three", "one")));
        assertThat(inPlace, is(Arrays.asList("two", "three", "one")));
        assertThat(copy, is(inPlace));
    }

    @Test
    public void testMapOfMultipleThings() throws Exception {
        String[] arr = {"foo", "bar", "baz", "buz", "biz"};
        int[] vals = {1, 2, 3, 4, 5};
        float[] floats = {1f, 2f, 3f, 4f, 5f};
        Set<String> set = new LinkedHashSet<>(Arrays.asList(arr));
        BufferCharSequenceSet bcsSet = new BufferCharSequenceSet(set);

        int[] indices = Arrays.stream(arr).mapToInt(bcsSet::getIndex).toArray();
        int[] newVals = Ints.toArray(Reorder.reorderCopy(Ints.asList(vals), indices));
        float[] newFloats = Floats.toArray(Reorder.reorderCopy(Floats.asList(floats), indices));

        for (int i = 0; i < arr.length; i++) {
            String word = arr[i];
            int index = bcsSet.getIndex(word);
            assertThat(newVals[index], is(vals[i]));
            assertThat(newFloats[index], is(floats[i]));
        }
    }
}