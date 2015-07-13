package com.github.scr.hashmap;

import com.github.scr.hashmap.maps.IndexedMap;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by scr on 7/3/15.
 */
public class WriterMain {
    public static void main(String[] args) throws IOException {
        @SuppressWarnings("unchecked")
        IndexedMap<CharSequence, Integer> map = (IndexedMap<CharSequence, Integer>) DataHelper.simple().next()[1];
        map.writePath(Paths.get("map.bin"));
    }
}
