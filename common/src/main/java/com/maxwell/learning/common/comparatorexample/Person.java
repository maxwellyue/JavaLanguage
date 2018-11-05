package com.maxwell.learning.common.comparatorexample;

import java.io.Serializable;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Person implements Comparable<Person>, Serializable {

    private String name;

    private int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        int res = 0;
        if(age > o.age){
            res = 1;
        }else if(age < o.age){
            res = -1;
        }
        return res;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s: %d", this.name, this.age);
    }
}
