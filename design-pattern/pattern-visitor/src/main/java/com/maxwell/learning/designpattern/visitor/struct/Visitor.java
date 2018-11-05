package com.maxwell.learning.designpattern.visitor.struct;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public interface Visitor {

    //访问元素A，相当于给元素A增加功能
    void visitConcreteElementA(ConcreteElementA elementA);

    //访问元素B，相当于给元素B增加功能
    void visitConcreteElementB(ConcreteElementB elementB);

}
