package org.github.scr.hashmap;

import org.github.scr.hashmap.collections.IndexedCollection;
import org.github.scr.hashmap.maps.IndexedMap;
import org.github.scr.hashmap.utils.CharSequenceIterator;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 7/2/15.
 */
@Test
public class KeysTest {
    @Test(dataProviderClass = DataHelper.class, dataProvider = "simple")
    public void testContainsAll(
            Map<String, Integer> initMap, IndexedMap<CharSequence, Integer> map) throws Exception {
        Set<CharSequence> keys = map.keySet();
        assertThat("contains all initializing keys", keys.containsAll(initMap.keySet()));
    }

    @Test(dataProviderClass = DataHelper.class, dataProvider = "simple")
    public void testKeysContainsEachKey(
            Map<String, Integer> initMap, IndexedMap<CharSequence, Integer> map) throws Exception {
        Set<CharSequence> keys = map.keySet();

        initMap.forEach((k, v) -> {
            assertThat("keys contains " + k, keys.contains(k));
        });
    }
}