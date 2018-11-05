package com.maxwell.learning.designpattern.visitor.example;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ServiceRequestVisitor implements Visitor {

    @Override
    public void visitPersonalCustomer(PersonalCustomer customer) {
        System.out.println("个人客户" + customer.getName() + "提出服务请求");
    }

    @Override
    public void visitEnterpriseCustomer(EnterpriseCustomer customer) {
        System.out.println("企业客户" + customer.getName() + "提出服务请求");
    }
}
