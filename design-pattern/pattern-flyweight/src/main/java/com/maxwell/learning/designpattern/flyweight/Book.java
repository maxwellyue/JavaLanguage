package com.maxwell.learning.designpattern.flyweight;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Book {

    private String bookName;
    private String author;
    private int price;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
