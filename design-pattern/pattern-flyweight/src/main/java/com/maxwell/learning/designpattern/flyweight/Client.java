package com.maxwell.learning.designpattern.flyweight;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args) {

        //假如只有10种书，每本书有1000个订单，
        // 这10*1000个订单实例则会共享这10个书的实例
        // 这样就可以节省[（10*1000 - 10 ）* 每个书的实例的大小]的内存空间
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String bookName = "book" + i;
            for (int j = 0; j < 1000; j++) {
                Order order = OrderFactory.generateOrder("xiaoming" + j, bookName);
                orders.add(order);
            }
        }
    }
}
