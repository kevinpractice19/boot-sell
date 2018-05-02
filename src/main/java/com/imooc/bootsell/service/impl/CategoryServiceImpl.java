package com.imooc.bootsell.service.impl;

import com.imooc.bootsell.entity.ProductCategory;
import com.imooc.bootsell.repository.ProductCategoryRepository;
import com.imooc.bootsell.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private ProductCategoryRepository repository;


    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findProductCategoryByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
