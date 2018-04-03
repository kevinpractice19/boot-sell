package com.imooc.bootsell.controller;

import com.imooc.bootsell.entity.ProductCategory;
import com.imooc.bootsell.entity.ProductInfo;
import com.imooc.bootsell.enums.ResultEnum;
import com.imooc.bootsell.exception.SellException;
import com.imooc.bootsell.form.ProductForm;
import com.imooc.bootsell.service.CategoryService;
import com.imooc.bootsell.service.ProductService;
import com.imooc.bootsell.utils.KeyUtil;
import com.imooc.bootsell.utils.ResultVoUtil;
import com.imooc.bootsell.utils.StaticUtil;
import com.imooc.bootsell.vo.ResultVo;
import javafx.scene.chart.ValueAxis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * 卖家商品
 */
@Slf4j
@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult, Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("/common/error", map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            if (!StringUtils.isEmpty(productForm.getProductId())) {
                productInfo = this.productService.findOne(productForm.getProductId());
            } else {
                productForm.setProductId(KeyUtil.getUniqueKey());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            productService.save(productInfo);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/seller/product/list");
        return new ModelAndView("/common/success", map);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = this.productService.findAll(pageRequest);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/product/list");
    }


    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId, Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = this.productService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = this.categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("/product/index");
    }

    /**
     * 商品上架
     *
     * @param productInfoId
     * @return
     */
    @RequestMapping(value = "/onSale", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView onSale(@RequestParam("productInfoId") String productInfoId, Map<String, Object> map) {
        try {
            productService.onSale(productInfoId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }


    /**
     * 商品下架
     *
     * @param productInfoId
     * @return
     */
    @RequestMapping(value = "/offSale", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView offSale(@RequestParam("productInfoId") String productInfoId, Map<String, Object> map) {
        try {
            productService.offSale(productInfoId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

}
