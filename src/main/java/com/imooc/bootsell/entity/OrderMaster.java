package com.imooc.bootsell.entity;

import com.imooc.bootsell.enums.OrderStatusEnum;
import com.imooc.bootsell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;  //订单总金额

    private Integer order_status = OrderStatusEnum.NEW.getCode();    /** 状态, 0 新下单 1. */

    private Integer pay_status = PayStatusEnum.WAIT.getCode();     /** 状态, 默认0未支付. */

//    private Date createTime;
//
//    private Date updateTime;

}
