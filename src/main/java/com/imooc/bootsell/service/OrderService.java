package com.imooc.bootsell.service;

import com.imooc.bootsell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO findOne(String orderId);

    OrderDTO cancel(OrderDTO orderDTO);

    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
}
