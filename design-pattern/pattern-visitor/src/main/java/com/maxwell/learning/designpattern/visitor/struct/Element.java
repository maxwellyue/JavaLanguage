package com.maxwell.learning.designpattern.visitor.struct;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public abstract class Element {

    public abstract void accept(Visitor visitor);
}
