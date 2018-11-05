package com.maxwell.learning.common.cloneexample;

import java.io.*;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Example {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Product product = new Product("book");
        Order order = new Order("123", product);

        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(order);
        oos.flush();
        //反序列化
        ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        Order serialOrder = (Order) ois.readObject();

        System.out.println(order);
        System.out.println(serialOrder);

        System.out.println(order.getProduct());
        System.out.println(serialOrder.getProduct());

    }
}
