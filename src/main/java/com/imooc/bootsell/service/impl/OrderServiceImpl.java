package com.imooc.bootsell.service.impl;

import com.imooc.bootsell.dto.CartDTO;
import com.imooc.bootsell.dto.OrderDTO;
import com.imooc.bootsell.entity.OrderDetail;
import com.imooc.bootsell.entity.OrderMaster;
import com.imooc.bootsell.entity.ProductInfo;
import com.imooc.bootsell.enums.OrderStatusEnum;
import com.imooc.bootsell.enums.PayStatusEnum;
import com.imooc.bootsell.enums.ResultEnum;
import com.imooc.bootsell.exception.SellException;
import com.imooc.bootsell.repository.OrderDetailRepository;
import com.imooc.bootsell.repository.OrderMasterRepository;
import com.imooc.bootsell.repository.ProductInfoRepository;
import com.imooc.bootsell.service.OrderService;
import com.imooc.bootsell.service.ProductService;
import com.imooc.bootsell.service.WebSocket;
import com.imooc.bootsell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private ProductInfoRepository infoRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private WebSocket webSocket;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //查询商品数量价格
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = infoRepository.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }
            //计算商品数量总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            //商品订单详情入库
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            BeanUtils.copyProperties(productInfo, orderDetail);
            detailRepository.save(orderDetail);

            //商品订单入库
            OrderMaster orderMaster = new OrderMaster();
            orderMaster.setOrderId(orderId);
            orderDTO.setOrderId(orderId);
            BeanUtils.copyProperties(orderDTO, orderMaster);
            orderMaster.setOrderAmount(orderAmount);
            orderMaster.setOrder_status(OrderStatusEnum.NEW.getCode());
            orderMaster.setPay_status(PayStatusEnum.WAIT.getCode());
            masterRepository.save(orderMaster);

            List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                    .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                    .collect(Collectors.toList());
            productService.decreaseStock(cartDTOList);

            //发送webSocket消息
            webSocket.sendWebSockets(orderDTO.getOrderId());
        }
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = this.masterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        List<OrderDetail> orderDetailList = this.detailRepository.findOrderDetailByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
