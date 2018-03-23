package com.imooc.bootsell.service;


import com.imooc.bootsell.entity.ProductCategory;

import java.util.List;


public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    /**
     * 查询商品类别
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);


}
