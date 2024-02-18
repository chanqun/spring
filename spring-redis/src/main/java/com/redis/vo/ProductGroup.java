package com.redis.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductGroup {
    private String productGroupId;

    private List<Product> productList;

    public ProductGroup(String productGroupId, List<Product> productList) {
        this.productGroupId = productGroupId;
        this.productList = productList;
    }
}
