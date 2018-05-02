package com.imooc.bootsell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/***
 * 创建订单所需参数
 */
@Data
public class OrderForm {

    @NotEmpty(message = "买家姓名必填")
    private String name;

    @NotEmpty(message = "买家地址必填")
    private String buyerAddress;

    @NotEmpty(message = "买家电话必填")
    private String phone;

    @NotEmpty(message = "买家微信openid必填")
    private String buyerOpenid;

    @NotEmpty(message = "购物车必填")
    private String items;
}
