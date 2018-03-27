package com.imooc.bootsell.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.bootsell.enums.ProductStatusEnum;
import com.imooc.bootsell.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    /**
     * 单价
     */
    private BigDecimal productPrice;

    /**
     * 库存
     */
    private Integer productStock;

    private String productDescription;

    private String productIcon;

    /** 状态, 0正常1下架. */

    private Integer productStatus = ProductStatusEnum.UP.getCode();

    private Integer categoryType;

//    private Date createTime;
//
//    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}
