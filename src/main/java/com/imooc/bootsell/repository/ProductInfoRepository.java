package com.imooc.bootsell.repository;

import com.imooc.bootsell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findProductInfoByProductStatus(Integer productStatus);
}
