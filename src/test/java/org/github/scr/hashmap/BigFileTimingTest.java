package org.github.scr.hashmap;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.github.scr.hashmap.maps.BufferCharSequenceMap;
import org.github.scr.hashmap.maps.BufferCharSequenceMaps;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.github.scr.hashmap.TestConstants.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by scr on 7/3/15.
 */
@Test
public class BigFileTimingTest {
    private static final Path WORDS_PATH = Paths.get("/usr/share/dict/words");
    private static final Path WORDS_BIN_PATH = TARGET_PATH.resolve("words.bin");
    private static final String KEY1 = "scientificophilosophical";
    private static final int TIMES = 100000;

    private static final long NUM_LINES = 235886L;


    private List<String> lines;
    private BufferCharSequenceMap<Integer> bufferedMap;
    private TObjectIntMap<String> troveMap = new TObjectIntHashMap<>();
    private Map<String, Integer> regularMap = new HashMap<>();

    @Test
    public void testReadAllLines() throws Exception {
        lines = Files.readAllLines(WORDS_PATH);
        assertThat(lines.size(), is((int) NUM_LINES));
    }

    @Test
    public void testReadStreamLines() throws Exception {
        try (BufferedReader input = Files.newBufferedReader(WORDS_PATH)) {
            assertThat(input.lines().count(), is(NUM_LINES));
        }
    }

    @Test(dependsOnMethods = "testReadAllLines")
    public void testLoadTroveMap() throws Exception {
        int i = 0;
        for (String line : lines) {
            troveMap.put(line, ++i);
        }
    }

    @Test(dependsOnMethods = "testReadAllLines")
    public void testLoadRegularMap() throws Exception {
        int i = 0;
        for (String line : lines) {
            regularMap.put(line, ++i);
        }
    }

    @Test(dependsOnMethods = "testLoadRegularMap")
    public void testSaveBufferedMap() throws Exception {
        BufferCharSequenceMap<Integer> map = BufferCharSequenceMaps.intMap(regularMap);
        map.writePath(WORDS_BIN_PATH);
        assertThat("words.bin exists", Files.exists(WORDS_BIN_PATH));
    }

    @Test(dependsOnMethods = "testSaveBufferedMap")
    public void testLoadBufferedMap() throws Exception {
        bufferedMap = BufferCharSequenceMaps.intMap(WORDS_BIN_PATH);
    }

    @Test(dependsOnMethods = "testLoadTroveMap")
    public void testIterateAllTrove() throws Exception {
        troveMap.forEachEntry((k, v) -> true);
    }

    @Test(dependsOnMethods = "testLoadRegularMap")
    public void testIterateAllRegular() throws Exception {
        regularMap.forEach((k, v) -> {
        });
    }

    @Test(dependsOnMethods = "testLoadTroveMap")
    public void testGetValsTrove() throws Exception {
        for (int i = 0; i < TIMES; ++i) {
            assertThat(troveMap.get(KEY1), not(is(0)));
        }
    }

    @Test(dependsOnMethods = "testLoadRegularMap")
    public void testGetValsRegular() throws Exception {
        for (int i = 0; i < TIMES; ++i) {
            assertThat(regularMap.get(KEY1), notNullValue());
        }
    }

    @Test(dependsOnMethods = "testLoadBufferedMap")
    public void testGetValsBuffered() throws Exception {
        for (int i = 0; i < TIMES; ++i) {
            assertThat(bufferedMap.get(KEY1), notNullValue());
        }
    }

    @Test(dependsOnMethods = {"testLoadRegularMap", "testLoadTroveMap", "testLoadBufferedMap"})
    public void testAllGetsSame() throws Exception {
        int val = regularMap.get(KEY1);
        assertThat(troveMap.get(KEY1), is(val));
        assertThat(bufferedMap.get(KEY1).intValue(), is(val));
    }
}
