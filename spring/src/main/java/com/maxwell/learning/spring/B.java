package com.maxwell.learning.spring;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class B {

    @Autowired
    private A a;

    public void setA(A a) {
        this.a = a;
    }
}
