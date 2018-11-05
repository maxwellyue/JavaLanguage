package com.maxwell.learning.designpattern.flyweight;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class OrderFactory {

    private static final Map<String, Book> books = new HashMap<>();

    public static Order generateOrder(String username, String bookName){
        Order order = new Order();
        order.setUsername(username);
        if(books.containsKey(bookName)){
            order.setBook(books.get(bookName));
        }else {
            Book book = createNewBookInstance(bookName);
            order.setBook(book);
            books.put(bookName,book );
        }
        return order;
    }

    private static Book createNewBookInstance(String bookName){
        //演示，实际可能是去数据库等获取具体信息
        Book book = new Book();
        book.setBookName(bookName);
        book.setAuthor("maxwell");
        book.setPrice(new Random().nextInt(100));
        return book;
    }

}
