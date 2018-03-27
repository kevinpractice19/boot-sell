package com.imooc.bootsell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.bootsell.entity.OrderDetail;
import com.imooc.bootsell.enums.OrderStatusEnum;
import com.imooc.bootsell.enums.PayStatusEnum;
import com.imooc.bootsell.utils.EnumUtil;
import com.imooc.bootsell.utils.serializer.Date2LongSerializer;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    /**
     * 订单id
     */
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private String orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getorderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);

    }


    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
