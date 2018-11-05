package com.maxwell.learning.common.comparatorexample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Example {

    public static void main(String[] args) {

        //初始化对象集合
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("a", 18));
        personList.add(new Person("aa", 6));
        personList.add(new Person("aaa", 32));
        personList.add(new Person("aaaa", 21));

        System.out.println("默认按年龄排序");
        Collections.sort(personList);
        personList.forEach(person -> {
            System.out.println(person);
        });


        System.out.println("自定义按名字的长度排序");
        Collections.sort(personList, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                int length1 = o1.getName().length();
                int length2 = o2.getName().length();
                int res = 0;
                if (length1 > length2) {
                    res = 1;
                } else if (length1 < length2) {
                    res = -1;
                }
                return res;
            }
        });
        personList.forEach(person -> {
            System.out.println(person);
        });
    }
}
