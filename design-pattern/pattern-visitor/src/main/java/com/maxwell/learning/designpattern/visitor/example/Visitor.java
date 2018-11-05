package com.maxwell.learning.designpattern.visitor.example;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public interface Visitor {

    void visitPersonalCustomer(PersonalCustomer customer);

    void visitEnterpriseCustomer(EnterpriseCustomer customer);
}
