package org.datagrouper.datagrouper.utils;

import java.util.HashMap;

public class HashMapUtils {
    private static final float LOAD_FACTOR = 0.75f;

    public static <K, V> HashMap<K, V> newHashMap(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("size cannot be less than 0.");
        }
        return new HashMap<>((int) (size / LOAD_FACTOR), LOAD_FACTOR);
    }
}
