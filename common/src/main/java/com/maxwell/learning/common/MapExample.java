package com.maxwell.learning.common;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class MapExample {

    public static void main(String[] args) {

        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("1", "one");
        hashMap.put("2", "two");
        hashMap.put("3", "three");

        Set<String> keySet = hashMap.keySet();
        for (String key : keySet) {
            System.out.println(String.format("[key]:%s, [value]%s", key, hashMap.get(key)));
        }


        hashMap.forEach((k, v) -> {

        });


        ConcurrentHashMap concurrentHashMap;

        int a = Integer.MAX_VALUE;

        LinkedHashMap linkedHashMap;

        AbstractMap abstractMap;

        Iterator iterator;

        TreeMap k;

        Comparator comparator;

    }

    @Test
    public void for1() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("1", "one");
        hashMap.put("2", "two");
        hashMap.put("3", "three");

        Set<String> keySet = hashMap.keySet();
        for (String key : keySet) {
            System.out.println(String.format("[key]:%s, [value]%s", key, hashMap.get(key)));
        }
    }

    @Test
    public void for2() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("1", "one");
        hashMap.put("2", "two");
        hashMap.put("3", "three");

        Collection<String> values = hashMap.values();
        for (String value : values) {
            System.out.println(String.format("[value]%s", value));
        }
    }

    @Test
    public void for3() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("1", "one");
        hashMap.put("2", "two");
        hashMap.put("3", "three");

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            System.out.println(String.format("[key]:%s, [value]%s", entry.getKey(), entry.getValue()));
        }
    }

    @Test
    public void for4() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("1", "one");
        hashMap.put("2", "two");
        hashMap.put("3", "three");

        hashMap.forEach((key, value)->{
            System.out.println(String.format("[key]:%s, [value]%s", key, value));
        });
    }
}
