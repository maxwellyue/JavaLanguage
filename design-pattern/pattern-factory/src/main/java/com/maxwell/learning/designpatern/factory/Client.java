package com.maxwell.learning.designpatern.factory;

import java.util.*;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args) {

        AbstractList abstractList;

        List<String> list = Arrays.asList("a", "b", "c", "d");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            iterator.next();
        }
    }
}
