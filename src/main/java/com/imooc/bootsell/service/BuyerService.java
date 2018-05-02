package com.imooc.bootsell.service;

import com.imooc.bootsell.dto.OrderDTO;

public interface BuyerService {

    //查询一个订单
    OrderDTO findOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
