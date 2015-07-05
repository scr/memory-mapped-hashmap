package org.github.scr.hashmap;

import org.github.scr.hashmap.maps.IndexedMap;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 7/3/15.
 */
@Test
public class EntriesTest {
    @Test(dataProviderClass = DataHelper.class, dataProvider = "simple")
    public void testEntries(
            Map<String, Integer> initMap, IndexedMap<CharSequence, Integer> map) throws Exception {
        Set<Map.Entry<CharSequence, Integer>> entries = map.entrySet();
        assertThat(entries.stream().count(), is(5L));
    }
}
