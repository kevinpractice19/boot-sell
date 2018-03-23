package com.imooc.bootsell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum {

    NEW(0, "新订单"),
    FINISHED(1, "订单完成"),
    CANCEL(2, "订单取消"),
    ;

    private Integer code;

    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
