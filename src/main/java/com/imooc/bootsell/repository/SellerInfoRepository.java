package com.imooc.bootsell.repository;

import com.imooc.bootsell.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findSellerInfoByOpenId(String openId);
}
