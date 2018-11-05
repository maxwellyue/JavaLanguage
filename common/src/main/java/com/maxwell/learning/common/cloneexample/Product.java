package com.maxwell.learning.common.cloneexample;

import java.io.Serializable;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Product implements Serializable {

    private String name;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
