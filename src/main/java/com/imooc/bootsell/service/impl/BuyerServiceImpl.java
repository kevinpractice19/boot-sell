package com.imooc.bootsell.service.impl;

import com.imooc.bootsell.dto.OrderDTO;
import com.imooc.bootsell.enums.ResultEnum;
import com.imooc.bootsell.exception.SellException;
import com.imooc.bootsell.service.BuyerService;
import com.imooc.bootsell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOne(String openid, String orderId) {
        return this.checkOwn(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO==null) {
            log.error("[取消订单]订单不存在,orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        return this.orderService.cancel(orderDTO);
    }

    public OrderDTO checkOwn(String openId, String orderId) {
        OrderDTO orderDTO = this.orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openId)) {
            log.error("订单的用户openid不一致,openid={},orderDto={}", openId, orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        return orderDTO;
    }
}
