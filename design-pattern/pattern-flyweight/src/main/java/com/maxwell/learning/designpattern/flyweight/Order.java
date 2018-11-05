package com.maxwell.learning.designpattern.flyweight;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Order {

    private String username;
    private Book book;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
