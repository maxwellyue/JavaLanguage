package com.maxwell.learning.common;

import org.junit.jupiter.api.Test;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class StringExample1 {

    @Test
    public void testIntern() {

        String s1 = "abc" + "def";
        System.out.println(s1.intern() == s1);
        System.out.println(s1 == "abcdef");
    }
}
