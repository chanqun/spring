package com.redis.vo;

import lombok.Data;

import java.util.List;

@Data
public class Keyword {

    private String keyword; // 유아용풍 -> H사 기저귀(PG0001), A사 딸랑이(PG0002)
    private List<ProductGroup> productGroupList;

    public Keyword(String keyword, List<ProductGroup> productGroupList) {
        this.keyword = keyword;
        this.productGroupList = productGroupList;
    }
}
