package com.maxwell.learning.designpattern.visitor.struct;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args) {

        ObjectStructure os = new ObjectStructure();
        os.addElement(new ConcreteElementA());
        os.addElement(new ConcreteElementB());

        Visitor visitor = new ConcreteVisitor1();
        os.handleRequest(visitor);

    }
}
