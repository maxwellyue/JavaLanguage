package com.maxwell.learning.common;

import org.junit.jupiter.api.Test;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class StringExample {

    private static final String test = "abcab";

    @Test
    public void testIntern() {

        String s = new StringBuilder("ja").append("va").toString();
        System.out.println(s.intern() == s);

        String s1 = new StringBuilder("abc").append("ab").toString();
        System.out.println(s1.intern() == s1);
    }


    public static void main(String[] args) throws Exception {
       /* //为长度为10的Integer数组随机赋值
        Integer[] sample = new Integer[10];
        Random random = new Random(1000);
        for (int i = 0; i < sample.length; i++) {
            sample[i] = random.nextInt();
        }
        //记录程序开始时间
        long t = System.currentTimeMillis();
        //使用/不使用intern方法为10万个String赋值，值来自于Integer数组的10个数
        for (int i = 0; i < MAX; i++) {
            arr[i] = new String(String.valueOf(sample[i % sample.length]));
            //arr[i] = new String(String.valueOf(sample[i % sample.length])).intern();
        }
        System.out.println((System.currentTimeMillis() - t) + "ms");
        System.gc();*/
    }

}
