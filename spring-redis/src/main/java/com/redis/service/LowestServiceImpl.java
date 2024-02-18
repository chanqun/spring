package com.redis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.vo.Keyword;
import com.redis.vo.Product;
import com.redis.vo.ProductGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LowestServiceImpl implements LowestPriceService {
    private final RedisTemplate redisTemplate;

    @Override
    public Set getSetValue(String key) {
        return redisTemplate.opsForZSet().rangeWithScores(key, 0, 9);
    }

    @Override
    public long setNewProduct(Product product) {
        redisTemplate.opsForZSet().add(product.getProductGroupId(), product.getProductId(), product.getPrice());

        return redisTemplate.opsForZSet().rank(product.getProductGroupId(), product.getProductId());
    }

    @Override
    public long setNewProductGroup(ProductGroup productGroup) {
        Product product = productGroup.getProductList().get(0);

        redisTemplate.opsForZSet().add(product.getProductGroupId(), product.getProductId(), product.getPrice());

        return redisTemplate.opsForZSet().zCard(productGroup.getProductGroupId());
    }

    @Override
    public long setNewProductGroupToKeyword(String keyword, String productGroupId, double score) {
        redisTemplate.opsForZSet().add(keyword, productGroupId, score);

        return redisTemplate.opsForZSet().rank(keyword, productGroupId);
    }

    @Override
    public Keyword getLowsetProductByKeyword(String keyword) {
        List<ProductGroup> productGroupList = getProductGroupByKeyword(keyword);

        return new Keyword(keyword, productGroupList);
    }

    public List<ProductGroup> getProductGroupByKeyword(String keyword) {
        List<ProductGroup> returnInfo = new ArrayList<>();
        List<String> productGroupList = List.copyOf(redisTemplate.opsForZSet().reverseRange(keyword, 0, 9));

        for (final String setProductGroupId : productGroupList) {
            List<Product> tempProductList = new ArrayList<>();
            Set prodAndPriceList = redisTemplate.opsForZSet().rangeWithScores(setProductGroupId, 0, 9);
            Iterator<Object> prodPriceObject = prodAndPriceList.iterator();

            while (prodPriceObject.hasNext()) {
                ObjectMapper objMapper = new ObjectMapper();
                Map<String, Object> prodPriceMap = objMapper.convertValue(prodPriceObject.next(), Map.class);
                Product tempProduct = new Product(setProductGroupId, prodPriceMap.get("value").toString(), Double.valueOf(prodPriceMap.get("score").toString()).intValue());

                tempProductList.add(tempProduct);
            }
            ProductGroup productGroup = new ProductGroup(setProductGroupId, tempProductList);
            returnInfo.add(productGroup);
        }

        return returnInfo;
    }
}
