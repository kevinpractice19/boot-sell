package com.imooc.bootsell.repository;

import com.imooc.bootsell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository
        extends JpaRepository<OrderMaster, String> {

    /**
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findOrderMastersByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
