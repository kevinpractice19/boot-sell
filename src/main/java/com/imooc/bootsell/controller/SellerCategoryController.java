package com.imooc.bootsell.controller;

import com.imooc.bootsell.entity.ProductCategory;
import com.imooc.bootsell.form.CategoryForm;
import com.imooc.bootsell.service.CategoryService;
import com.imooc.bootsell.utils.ResultVoUtil;
import com.imooc.bootsell.utils.StaticUtil;
import com.imooc.bootsell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 卖家类目
 */

@Controller
@RequestMapping(value = "/seller/category")
public class SellerCategoryController {

    @Resource(name = "categoryService")
    private CategoryService categoryService;


    /**
     * @param categoryId
     * @param map
     * @return
     */
    @RequestMapping(value = "/findOne", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView findOne(@RequestParam(value = "categoryId", required = false) Integer categoryId, Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory productCategory = this.categoryService.findOne(categoryId);
            map.put("productCategory", productCategory);
        }
        return new ModelAndView("/category/index", map);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView findAll(Map<String, Object> map) {
        List<ProductCategory> categoryList = this.categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("/category/list", map);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult, Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/category/findOne");
            return new ModelAndView("common/error", map);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if (categoryForm.getCategoryId() != null) {
                productCategory = this.categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, productCategory);
            this.categoryService.save(productCategory);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/category/findOne");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/seller/category/findAll");
        return new ModelAndView("common/success", map);
    }


}
