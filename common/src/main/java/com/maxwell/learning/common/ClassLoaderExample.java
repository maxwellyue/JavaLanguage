package com.maxwell.learning.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ClassLoaderExample {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        ClassLoader classLoader = list.getClass().getClassLoader();



        Person person = new Person();
        ClassLoader classLoader1 = person.getClass().getClassLoader();



        int a = 1;

    }
}
