package com.maxwell.learning.designpattern.obderver.guava;

import com.google.common.eventbus.EventBus;

/**
 *
 * 实现功能：
 *
 * 当有新员工入职时，为其开通公司内部系统账号，并为其制作工牌
 * 当有员工离职时，为其关闭公司内部系统账号，并收回其工牌
 *
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args){
        EventBus eventBus = new EventBus();
        eventBus.register(new EmployeeEventSubscriber());

        Employee employee = new Employee();
        employee.setName("xiaoming");
        employee.setStatus(Employee.Status.NEW);
        eventBus.post(employee);

        System.out.println("1 year later...");
        employee.setStatus(Employee.Status.LEAVE);
        eventBus.post(employee);
    }

}
