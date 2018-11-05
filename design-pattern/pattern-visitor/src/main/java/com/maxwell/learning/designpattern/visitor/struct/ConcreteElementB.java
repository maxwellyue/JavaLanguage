package com.maxwell.learning.designpattern.visitor.struct;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ConcreteElementB extends Element{
    @Override
    public void accept(Visitor visitor) {
        visitor.visitConcreteElementB(this);
    }
}
