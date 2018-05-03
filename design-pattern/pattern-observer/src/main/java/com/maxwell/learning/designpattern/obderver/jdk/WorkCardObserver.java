package com.maxwell.learning.designpattern.obderver.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class WorkCardObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Employee employee = (Employee)o;
        Employee.Status status = employee.getStatus();
        if(status == Employee.Status.NEW){
            System.out.println(String.format("make an work card for [employee : %s]", employee.getName()));
        }else if(status == Employee.Status.LEAVE){
            System.out.println(String.format("reclaim [employee : %s] work card", employee.getName()));
        }
    }
}
