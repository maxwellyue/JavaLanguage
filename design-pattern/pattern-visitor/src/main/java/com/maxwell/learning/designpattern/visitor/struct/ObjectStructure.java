package com.maxwell.learning.designpattern.visitor.struct;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ObjectStructure {

    private Collection<Element> collection = new ArrayList<>();

    public void addElement(Element e){
        this.collection.add(e);
    }

    public void handleRequest(Visitor visitor){
        for (Element e : collection){
            e.accept(visitor);
        }
    }
}
