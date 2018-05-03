package com.maxwell.learning.designpattern.obderver.jdk;

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

        Employee employee = new Employee();
        employee.setName("xiaoming");

        employee.addObserver(new AccountObserver());
        employee.addObserver(new WorkCardObserver());

        employee.setStatus(Employee.Status.NEW);
        System.out.println("1 year later...");
        employee.setStatus(Employee.Status.LEAVE);

    }

}
