package com.github.scr.hashmap.utils;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class Reorder {
    public static <E> List<E> reorderInplace(List<E> list, int[] indices) {
        int length = indices.length;
        Preconditions.checkArgument(length == list.size());

        // Fix all elements one by one
        for (int i = 0; i < length; i++) {
            // While index[i] and arr[i] are not fixed
            while (indices[i] != i) {
                // Store values of the target (or correct)
                // position before placing arr[i] there
                int oldTargetI = indices[indices[i]];
                E oldTargetE = list.get(indices[i]);

                // Place arr[i] at its target (or correct)
                // position. Also copy corrected index for
                // new position
                list.set(indices[i], list.get(i));
                indices[indices[i]] = indices[i];

                // Copy old target values to arr[i] and
                // index[i]
                indices[i] = oldTargetI;
                list.set(i, oldTargetE);
            }

        }
        return list;
    }

    public static <E> List<E> reorderCopy(List<E> list, int[] indices) {
        int length = indices.length;
        Preconditions.checkArgument(length == list.size());
        int[] reverseIndices = new int[length];
        for (int i = 0; i < length; i++) {
            reverseIndices[indices[i]] = i;
        }
        List<E> ret = new ArrayList<>(length);
        for (int index : reverseIndices) {
            ret.add(list.get(index));
        }
        return ret;
    }
}
