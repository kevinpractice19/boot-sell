package com.imooc.bootsell.service.impl;

import com.imooc.bootsell.entity.ProductInfo;
import com.imooc.bootsell.enums.ProductStatusEnum;
import com.imooc.bootsell.repository.ProductInfoRepository;
import com.imooc.bootsell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return this.repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findAll() {
        return this.repository.findAll();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return this.repository.findProductInfoByProductStatus(ProductStatusEnum.UP.getCode());
    }
}
