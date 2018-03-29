package com.imooc.bootsell.controller;

import com.imooc.bootsell.converter.OrderForm2OrderDTOConverter;
import com.imooc.bootsell.dto.OrderDTO;
import com.imooc.bootsell.enums.ResultEnum;
import com.imooc.bootsell.exception.SellException;
import com.imooc.bootsell.form.OrderForm;
import com.imooc.bootsell.service.OrderService;
import com.imooc.bootsell.utils.ResultVoUtil;
import com.imooc.bootsell.utils.StaticUtil;
import com.imooc.bootsell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;


    //BindingResult用来做参数校验
    @RequestMapping(value = "/createBuyerOrder", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<Map<String, Object>> createBuyerOrder(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("[创建订单]参数不正确,orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[创建订单]购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.createOrder(orderDTO);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("orderId",createResult.getOrderId());
        return ResultVoUtil.success(stringStringMap);
    }



}
