package com.redis.controller;

import com.redis.service.LowestPriceService;
import com.redis.vo.Keyword;
import com.redis.vo.Product;
import com.redis.vo.ProductGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LowestPriceController {
    private final LowestPriceService lowestPriceService;

    @GetMapping("/set-value")
    public Set getSetValue(String key) {
        return lowestPriceService.getSetValue(key);
    }

    @PostMapping("/product")
    public long setNewProduct(@RequestBody Product product) {
        return lowestPriceService.setNewProduct(product);
    }

    @PostMapping("/product-group")
    public long setNewProductGroup(@RequestBody ProductGroup productGroup) {
        return lowestPriceService.setNewProductGroup(productGroup);
    }

    @PostMapping("/product-group-to-keyword")
    public long setNewProductGroup(String keyword, String productGroupId, double score) {
        return lowestPriceService.setNewProductGroupToKeyword(keyword, productGroupId, score);
    }

    @GetMapping("/product-price/lowest")
    public Keyword getLowsetProductByKeyword(String keyword) {
        return lowestPriceService.getLowsetProductByKeyword(keyword);
    }
}
