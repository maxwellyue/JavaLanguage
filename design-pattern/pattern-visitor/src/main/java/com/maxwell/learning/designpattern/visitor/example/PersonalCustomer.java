package com.maxwell.learning.designpattern.visitor.example;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class PersonalCustomer extends Customer {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitPersonalCustomer(this);
    }
}
