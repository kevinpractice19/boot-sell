package com.imooc.bootsell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SellerInfo {

    @Id
    private Integer sellerId;

    private String userName;

    private String password;

    private String openId;

}
