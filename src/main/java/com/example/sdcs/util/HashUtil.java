package com.example.sdcs.util;


public class HashUtil {
    public static int hashToIndex(String key, int size) {
        return Math.abs(key.hashCode()) % size;
    }
}
