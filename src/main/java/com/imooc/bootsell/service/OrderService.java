package com.imooc.bootsell.service;

import com.imooc.bootsell.dto.OrderDTO;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO findOne(String orderId);
}
