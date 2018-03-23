package com.imooc.bootsell.controller;

import com.imooc.bootsell.entity.ProductInfo;
import com.imooc.bootsell.service.ProductService;
import com.imooc.bootsell.utils.ResultVoUtil;
import com.imooc.bootsell.utils.StaticUtil;
import com.imooc.bootsell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 卖家商品
 */
@RestController
@RequestMapping("/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo findAll(){
        List<ProductInfo> productInfoList = this.productService.findAll();
        return ResultVoUtil.success(productInfoList);
    }

}
