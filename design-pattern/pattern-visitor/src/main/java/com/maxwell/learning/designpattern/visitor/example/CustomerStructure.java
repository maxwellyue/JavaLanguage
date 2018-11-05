package com.maxwell.learning.designpattern.visitor.example;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class CustomerStructure {

    private Collection<Customer> collection = new ArrayList<>();

    public void addElement(Customer customer){
        this.collection.add(customer);
    }

    public void handleRequest(Visitor visitor){
        for (Customer customer : collection){
            customer.accept(visitor);
        }
    }
}
