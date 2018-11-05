package com.maxwell.learning.common.annotationsexample;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Operation {

    @Log(type = "添加")
    public void add(Book book) {
        System.out.println("add book:" + book.getName());
    }

    @Log(type = "删除")
    public void delete(Book book) {
        System.out.println("delete book:" + book.getName());
    }

    static class Book {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
