package com.imooc.bootsell.controller;

import com.imooc.bootsell.converter.OrderForm2OrderDTOConverter;
import com.imooc.bootsell.dto.OrderDTO;
import com.imooc.bootsell.enums.ResultEnum;
import com.imooc.bootsell.exception.SellException;
import com.imooc.bootsell.form.OrderForm;
import com.imooc.bootsell.service.BuyerService;
import com.imooc.bootsell.service.OrderService;
import com.imooc.bootsell.utils.ResultVoUtil;
import com.imooc.bootsell.utils.StaticUtil;
import com.imooc.bootsell.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家订单
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;


    /**
     * 创建订单
     *
     * @param orderForm
     * @param bindingResult
     * @return
     */
    //BindingResult用来做参数校验
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @RequestMapping(value = "/createBuyerOrder", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<Map<String, Object>> createBuyerOrder(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("[创建订单]参数不正确,orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[创建订单]购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.createOrder(orderDTO);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("orderId", createResult.getOrderId());
        return ResultVoUtil.success(stringStringMap);
    }


    /**
     * 查询订单列表
     *
     * @param openid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<List<OrderDTO>> findList(@RequestParam("openid") String openid, @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单列表]openid为空openid={}", openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<OrderDTO> dtoPage = this.orderService.findList(openid, pageRequest);
        return ResultVoUtil.success(dtoPage.getContent());
    }


    @RequestMapping(value = "/detail", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = this.buyerService.findOne(openid, orderId);
        return ResultVoUtil.success(orderDTO);
    }


    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo cancelOrder(@RequestParam("orderId") String orderId, @RequestParam("openid") String openid) {
        this.buyerService.cancelOrder(openid, orderId);
        return ResultVoUtil.success();
    }


}
