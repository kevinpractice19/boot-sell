package com.imooc.bootsell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *商品（包含类目）
 */
@Data
public class ProductVo {

    @JsonProperty(value = "name")
    private String categoryName;

    @JsonProperty(value = "type")
    private Integer categoryType;

    @JsonProperty(value = "foods")
    private List<ProductInfoVo> productInfoVoList;
}
