package org.github.scr.hashmap;

import org.github.scr.hashmap.maps.IndexedMap;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 7/2/15.
 */
@Test
public class ValuesTest {
    @Test(dataProviderClass = DataHelper.class, dataProvider = "simple")
    public void testValues(
            Map<String, Integer> initMap, IndexedMap<CharSequence, Integer> map) throws Exception {
        assertThat("have all init values", map.values().containsAll(initMap.values()));
        assertThat(map.values(), hasItems(1, 2, 3, 4, 5));
    }
}
