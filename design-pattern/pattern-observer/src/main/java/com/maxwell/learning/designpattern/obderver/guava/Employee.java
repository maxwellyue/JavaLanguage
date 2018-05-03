package com.maxwell.learning.designpattern.obderver.guava;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Employee {

    private String name;

    private Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    enum Status {
        NEW, LEAVE
    }
}
