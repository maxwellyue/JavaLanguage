package com.maxwell.learning.common.cloneexample;

import java.io.Serializable;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Order implements Serializable {

    private String id;

    private Product product;

    public Order(String id, Product product) {
        this.id = id;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
