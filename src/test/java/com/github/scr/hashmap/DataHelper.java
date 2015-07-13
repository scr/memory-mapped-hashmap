package com.github.scr.hashmap;

import com.github.scr.hashmap.maps.BufferCharSequenceMaps;
import com.github.scr.hashmap.maps.IndexedMap;
import com.google.common.collect.Iterators;
import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by scr on 7/2/15.
 */
public class DataHelper {
    @DataProvider(name = "simple")
    public static Iterator<Object[]> simple() {
        return Iterators.transform(simpleInit(), data -> {
            @SuppressWarnings("unchecked")
            Map<String, Integer> initMap = (Map<String, Integer>) data[0];
            IndexedMap<CharSequence, Integer> map = BufferCharSequenceMaps.intMap(initMap);
            return new Object[]{initMap, map};
        });
    }

    @DataProvider(name = "simpleInit")
    public static Iterator<Object[]> simpleInit() {
        Map<String, Integer> initMap = new HashMap<>();
        initMap.put("one", 1);
        initMap.put("two", 2);
        initMap.put("three", 3);
        initMap.put("four", 4);
        initMap.put("five", 5);

        return Iterators.singletonIterator(new Object[]{initMap});
    }

    @DataProvider(name = "simpleInitFloat")
    public static Iterator<Object[]> simpleInitFloat() {
        Map<String, Float> initMap = new HashMap<>();
        initMap.put("one", 1f);
        initMap.put("two", 2f);
        initMap.put("three", 3f);
        initMap.put("four", 4f);
        initMap.put("five", 5f);

        return Iterators.singletonIterator(new Object[]{initMap});
    }
}
