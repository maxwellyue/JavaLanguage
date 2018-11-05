package com.maxwell.learning.designpattern.visitor.example;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class WorthAnalyzeVisitor implements Visitor {
    @Override
    public void visitPersonalCustomer(PersonalCustomer customer) {
        System.out.println("对个人客户" + customer.getName() + "进行价值分析");
    }

    @Override
    public void visitEnterpriseCustomer(EnterpriseCustomer customer) {
        System.out.println("对企业客户" + customer.getName() + "进行价值分析");
    }
}
