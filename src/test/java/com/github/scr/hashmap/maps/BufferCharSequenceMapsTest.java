package com.github.scr.hashmap.maps;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 7/4/15.
 */
@Test
public class BufferCharSequenceMapsTest {
    @Test
    public void testIntMap() throws Exception {
        Map<String, Integer> initMap = new HashMap<>();
        initMap.put("One hundred twenty-three", 123);
        initMap.put("Four hundred fifty-six", 456);

        IndexedMap<CharSequence, Integer> map = BufferCharSequenceMaps.intMap(initMap);
        assertThat("Contains all initMap keys", map.keySet().containsAll(initMap.keySet()));
        assertThat("Contains all initMap values", map.values().containsAll(initMap.values()));

        assertThat(map.get("One hundred twenty-three").intValue(), is(123));
    }

    @Test
    public void testFloatMap() throws Exception {
        Map<String, Float> initMap = new HashMap<>();
        initMap.put("One hundred twenty-three", 123f);
        initMap.put("Four hundred fifty-six", 456f);

        IndexedMap<CharSequence, Float> map = BufferCharSequenceMaps.floatMap(initMap);
        assertThat("Contains all initMap keys", map.keySet().containsAll(initMap.keySet()));
        assertThat("Contains all initMap values", map.values().containsAll(initMap.values()));

        assertThat(map.get("One hundred twenty-three").floatValue(), is(123f));
    }

    @Test
    public void testDoubleMap() throws Exception {
        Map<String, Double> initMap = new HashMap<>();
        initMap.put("One hundred twenty-three", 123d);
        initMap.put("Four hundred fifty-six", 456d);

        IndexedMap<CharSequence, Double> map = BufferCharSequenceMaps.doubleMap(initMap);
        assertThat("Contains all initMap keys", map.keySet().containsAll(initMap.keySet()));
        assertThat("Contains all initMap values", map.values().containsAll(initMap.values()));

        assertThat(map.get("One hundred twenty-three").doubleValue(), is(123d));
    }

    @Test
    public void testLongMap() throws Exception {
        Map<String, Long> initMap = new HashMap<>();
        initMap.put("One hundred twenty-three", 123L);
        initMap.put("Four hundred fifty-six", 456L);

        IndexedMap<CharSequence, Long> map = BufferCharSequenceMaps.longMap(initMap);
        assertThat("Contains all initMap keys", map.keySet().containsAll(initMap.keySet()));
        assertThat("Contains all initMap values", map.values().containsAll(initMap.values()));

        assertThat(map.get("One hundred twenty-three").longValue(), is(123L));
    }

    @Test
    public void testShortMap() throws Exception {
        Map<String, Short> initMap = new HashMap<>();
        initMap.put("One hundred twenty-three", (short) 123);
        initMap.put("Four hundred fifty-six", (short) 456);

        IndexedMap<CharSequence, Short> map = BufferCharSequenceMaps.shortMap(initMap);
        assertThat("Contains all initMap keys", map.keySet().containsAll(initMap.keySet()));
        assertThat("Contains all initMap values", map.values().containsAll(initMap.values()));

        assertThat(map.get("One hundred twenty-three").shortValue(), is((short) 123));
    }

    @Test
    public void testByteMap() throws Exception {
        Map<String, Byte> initMap = new HashMap<>();
        initMap.put("One hundred twenty-three", (byte) 123);
        initMap.put("Four hundred fifty-six", (byte) 456);

        IndexedMap<CharSequence, Byte> map = BufferCharSequenceMaps.byteMap(initMap);
        assertThat("Contains all initMap keys", map.keySet().containsAll(initMap.keySet()));
        assertThat("Contains all initMap values", map.values().containsAll(initMap.values()));

        assertThat(map.get("One hundred twenty-three").byteValue(), is((byte) 123));
    }

    @Test
    public void testCharacterMap() throws Exception {
        Map<String, Character> initMap = new HashMap<>();
        initMap.put("One hundred twenty-three", (char) 123);
        initMap.put("Four hundred fifty-six", (char) 456);

        IndexedMap<CharSequence, Character> map = BufferCharSequenceMaps.characterMap(initMap);
        assertThat("Contains all initMap keys", map.keySet().containsAll(initMap.keySet()));
        assertThat("Contains all initMap values", map.values().containsAll(initMap.values()));

        assertThat(map.get("One hundred twenty-three").charValue(), is((char) 123));
    }
}