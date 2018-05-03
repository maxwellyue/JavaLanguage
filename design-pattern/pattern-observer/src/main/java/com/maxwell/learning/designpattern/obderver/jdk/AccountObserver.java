package com.maxwell.learning.designpattern.obderver.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class AccountObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Employee employee = (Employee)o;
        Employee.Status status = employee.getStatus();
        if(status == Employee.Status.NEW){
            System.out.println(String.format("create account for [employee : %s]", employee.getName()));
        }else if(status == Employee.Status.LEAVE){
            System.out.println(String.format("delete account for [employee : %s]", employee.getName()));
        }
    }

}
