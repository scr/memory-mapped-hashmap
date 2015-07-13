package com.github.scr.hashmap;

import com.github.scr.hashmap.maps.BufferCharSequenceMaps;
import com.github.scr.hashmap.maps.IndexedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 7/3/15.
 */
@Test
public class IOTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(IOTest.class);
    private static final Path outputDir = Paths.get("target");

    @Test(dataProviderClass = DataHelper.class, dataProvider = "simpleInit")
    public void testIOInt(Map<String, Integer> initMap) throws Exception {
        IndexedMap<CharSequence, Integer> map = BufferCharSequenceMaps.intMap(initMap);
        Path filePath = outputDir.resolve("intMap.bin");
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            // Ok if doesn't exist already.
        }
        assertThat("output doesn't exist", Files.notExists(filePath));
        map.writePath(filePath);
        assertThat("output exists", Files.exists(filePath));
        IndexedMap<CharSequence, Integer> readMap = BufferCharSequenceMaps.intMap(filePath);
        assertThat(readMap, notNullValue());

        readMap.forEach((k, v) -> LOGGER.debug("{} -> {}", k, v));
        assertThat(readMap.get("three"), is(3));
        LOGGER.debug("checking");
        map.forEach((k, v) -> {
            LOGGER.debug("asserting {} -> {}", k, v);
            assertThat(readMap.get(k), is(v));
        });
    }

    @Test(dataProviderClass = DataHelper.class, dataProvider = "simpleInitFloat")
    public void testIOFloat(Map<String, Float> initMap) throws Exception {
        IndexedMap<CharSequence, Float> map = BufferCharSequenceMaps.floatMap(initMap);
        Path filePath = outputDir.resolve("intMap.bin");
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            // Ok if doesn't exist already.
        }
        assertThat("output doesn't exist", Files.notExists(filePath));
        map.writePath(filePath);
        assertThat("output exists", Files.exists(filePath));
        IndexedMap<CharSequence, Float> readMap = BufferCharSequenceMaps.floatMap(filePath);
        assertThat(readMap, notNullValue());

        readMap.forEach((k, v) -> LOGGER.debug("{} -> {}", k, v));
        assertThat(readMap.get("three"), is(3f));
        LOGGER.debug("checking");
        map.forEach((k, v) -> {
            LOGGER.debug("asserting {} -> {}", k, v);
            assertThat(readMap.get(k), is(v));
        });
    }
}
