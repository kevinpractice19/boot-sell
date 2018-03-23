package com.imooc.bootsell.controller;

import com.imooc.bootsell.entity.ProductCategory;
import com.imooc.bootsell.form.CategoryForm;
import com.imooc.bootsell.service.CategoryService;
import com.imooc.bootsell.utils.ResultVoUtil;
import com.imooc.bootsell.utils.StaticUtil;
import com.imooc.bootsell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 卖家类目
 */

@RestController
@RequestMapping(value = "/category")
public class SellerCategoryController {

    @Resource(name = "categoryService")
    private CategoryService categoryService;



    @RequestMapping(value = "/findOne", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<ProductCategory> findOne(@RequestParam("categoryId") Integer categoryId) {
        ProductCategory productCategory = this.categoryService.findOne(categoryId);
        return ResultVoUtil.success(productCategory);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<List<ProductCategory>> findAll() {
        List<ProductCategory> categoryList = this.categoryService.findAll();
        return ResultVoUtil.success(categoryList);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<ProductCategory> save(@RequestBody CategoryForm categoryForm) {
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setCreateTime(new Date());
        productCategory1.setUpdateTime(new Date());
        BeanUtils.copyProperties(categoryForm, productCategory1);
        return ResultVoUtil.success(this.categoryService.save(productCategory1));

    }


}
