package com.imooc.bootsell.controller;

import com.imooc.bootsell.entity.ProductInfo;
import com.imooc.bootsell.enums.ResultEnum;
import com.imooc.bootsell.exception.SellException;
import com.imooc.bootsell.form.ProductForm;
import com.imooc.bootsell.service.ProductService;
import com.imooc.bootsell.utils.KeyUtil;
import com.imooc.bootsell.utils.ResultVoUtil;
import com.imooc.bootsell.utils.StaticUtil;
import com.imooc.bootsell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * 卖家商品
 */
@Slf4j
@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo save(@Valid ProductForm productForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建商品]传参错误orderForm={}", productForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        ProductInfo productInfo = new ProductInfo();
        if (!StringUtils.isEmpty(productForm.getProductId())) {
            log.info("[创建商品]商品id已经存在,productId={}", productForm.getProductId());
            productInfo = this.productService.findOne(productForm.getProductId());
        } else {
            productForm.setProductId(KeyUtil.getUniqueKey());
        }
        BeanUtils.copyProperties(productForm, productInfo);
        return ResultVoUtil.success(this.productService.save(productInfo));
    }


    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo findAll() {
        List<ProductInfo> productInfoList = this.productService.findAll();
        return ResultVoUtil.success(productInfoList);
    }

    /**
     * 商品上架
     *
     * @param productInfoId
     * @return
     */
    @RequestMapping(value = "/onSale", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo onSale(@RequestParam("productInfoId") String productInfoId) {
        ProductInfo productInfo = this.productService.onSale(productInfoId);
        return ResultVoUtil.success(productInfo);

    }

    /**
     * 商品下架
     *
     * @param productInfoId
     * @return
     */
    @RequestMapping(value = "/offSale", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo offSale(@RequestParam("productInfoId") String productInfoId) {
        ProductInfo productInfo = this.productService.offSale(productInfoId);
        return ResultVoUtil.success(productInfo);
    }

}
