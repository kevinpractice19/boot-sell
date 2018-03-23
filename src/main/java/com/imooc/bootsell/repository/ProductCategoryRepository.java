package com.imooc.bootsell.repository;


import com.imooc.bootsell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findProductCategoryByCategoryTypeIn(List<Integer> categoryTypeList);
}
