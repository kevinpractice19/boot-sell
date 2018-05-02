package com.imooc.bootsell.service;

import com.imooc.bootsell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO findOne(String orderId);

    OrderDTO cancel(OrderDTO orderDTO);

    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /** 查询订单列表. */
    Page<OrderDTO> findList(Pageable pageable);

    /** 完结订单. */
    OrderDTO finish(OrderDTO orderDTO);

}
