package com.imooc.bootsell.service.impl;

import com.imooc.bootsell.converter.OrderMaster2OrderDTOConverter;
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
import com.imooc.bootsell.service.PushMessageService;
import com.imooc.bootsell.service.WebSocket;
import com.imooc.bootsell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private PushMessageService pushMessageService;

    /**
     * 创建订单
     *
     * @param orderDTO
     * @return
     */

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
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
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

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        //查询订单状态
        OrderMaster orderMaster = new OrderMaster();
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.CANCEL)) {
            log.error("[检查订单状态]订单状态不正确,orderStatus", orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //取消订单
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster orderMasterResult = masterRepository.save(orderMaster);
        if (orderMasterResult == null) {
            log.error("[取消订单]取消订单失败,orderMasterResult={}", orderMasterResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[取消订单]订单中没有商品详情,orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(orderDetail -> new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()))
                .collect(Collectors.toList());

        //如果已经支付,需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
//            paryService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> dtoPage = this.masterRepository.findOrderMastersByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(dtoPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, dtoPage.getTotalElements());
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = masterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW)) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateMaster = this.masterRepository.save(orderMaster);
        if (updateMaster == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //推送微信消息模版
        pushMessageService.orderStatus(orderDTO);
        return orderDTO;
    }


}
