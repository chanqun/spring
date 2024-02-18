package com.redis.vo;

import lombok.Data;

@Data
public class Product {

    private String productGroupId;
    private String productId;
    private int price;

    public Product() {
    }

    public Product(String productGroupId, String productId, int price) {
        this.productGroupId = productGroupId;
        this.productId = productId;
        this.price = price;
    }
}
