package com.maxwell.learning.common;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class PassByValue {

    public static void main(String[] args) {
        int num = 5;
        addNum(num);
        System.err.println(num);
    }

    static void addNum(int a) {
        a = a + 10;
    }

}
