package com.maxwell.learning.designpatern.factory.simplefactory;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Factory {

    public static A createA(int condition) {
        A instance = null;

        if (condition == 1) {
            instance = new A1Impl();
        } else if (condition == 2) {
            instance = new A2Impl();
        }
        return instance;
    }

}

interface A {
    void a();
}

class A1Impl implements A {
    @Override
    public void a() {

    }
}

class A2Impl implements A {
    @Override
    public void a() {

    }
}