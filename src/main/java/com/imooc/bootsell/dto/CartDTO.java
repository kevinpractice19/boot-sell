package com.imooc.bootsell.dto;

import lombok.Data;

/**
 * 购物车
 */
@Data
public class CartDTO {

    /**
     * 商品id
     */
    private String productInfoId;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDTO(String productInfoId, Integer productQuantity) {
        this.productInfoId = productInfoId;
        this.productQuantity = productQuantity;
    }
}
