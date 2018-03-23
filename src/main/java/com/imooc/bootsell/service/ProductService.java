package com.imooc.bootsell.service;

import com.imooc.bootsell.entity.ProductInfo;

import java.util.List;

public interface ProductService {


    ProductInfo findOne(String productId);

    List<ProductInfo> findAll();

    /**
     * 查询所有的在架商品
     * @return
     */
    List<ProductInfo> findUpAll();
}
