package com.imooc.bootsell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 商品详情
 */
@Data
public class ProductInfoVo {

    @JsonProperty(value = "id")
    private Integer productId;

    @JsonProperty(value = "Name")
    private String productName;

    @JsonProperty(value = "Price")
    private BigDecimal productPrice;

    @JsonProperty(value = "description")
    private String productDescription;

    @JsonProperty(value = "icon")
    private String productIcon;
}
