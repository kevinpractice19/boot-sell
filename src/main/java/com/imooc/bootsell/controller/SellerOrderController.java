package com.imooc.bootsell.controller;

import com.imooc.bootsell.service.OrderService;
import com.imooc.bootsell.utils.StaticUtil;
import com.imooc.bootsell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/seller/sellOrder")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo cancel(@RequestParam("orderId")String orderId){
        return null;

    }
}
