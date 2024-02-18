package com.redis.service;


import com.redis.vo.Keyword;
import com.redis.vo.Product;
import com.redis.vo.ProductGroup;

import java.util.Set;

public interface LowestPriceService {

    Set getSetValue(String key);


    long setNewProduct(Product product);

    long setNewProductGroup(ProductGroup productGroup);

    long setNewProductGroupToKeyword(String keyword, String productGroupId, double score);

    Keyword getLowsetProductByKeyword(String keyword);
}
