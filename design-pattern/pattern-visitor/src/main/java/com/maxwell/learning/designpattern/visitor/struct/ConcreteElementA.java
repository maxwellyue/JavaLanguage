package com.maxwell.learning.designpattern.visitor.struct;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ConcreteElementA extends Element{
    @Override
    public void accept(Visitor visitor) {
        visitor.visitConcreteElementA(this);
    }
}
