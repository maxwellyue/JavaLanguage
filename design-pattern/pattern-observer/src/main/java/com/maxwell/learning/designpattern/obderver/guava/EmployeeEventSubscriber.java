package com.maxwell.learning.designpattern.obderver.guava;

import com.google.common.eventbus.Subscribe;

/**
 *
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class EmployeeEventSubscriber {

    @Subscribe
    public void handleNew(Employee employee) {
        System.out.println(String.format("create account for [employee : %s]", employee.getName()));
        System.out.println(String.format("make an work card for [employee : %s]", employee.getName()));
    }

    @Subscribe
    public void handleLeave(Employee employee) {
        System.out.println(String.format("delete account for [employee : %s]", employee.getName()));
        System.out.println(String.format("reclaim [employee : %s] work card", employee.getName()));
    }
}
