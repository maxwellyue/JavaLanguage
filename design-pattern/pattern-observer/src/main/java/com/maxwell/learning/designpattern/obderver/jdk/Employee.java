package com.maxwell.learning.designpattern.obderver.jdk;

import java.util.Observable;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Employee extends Observable {

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
        //有员工变动，则手动设置目标变化
        this.setChanged();
        //说明有员工入职
        if(status == Status.NEW){
            this.notifyObservers(this);
        }else if(status == Status.LEAVE){
            this.notifyObservers(this);
        }
    }

    enum Status{
        NEW, LEAVE
    }
}
