package com.imooc.bootsell.controller;

import com.imooc.bootsell.dto.OrderDTO;
import com.imooc.bootsell.enums.ResultEnum;
import com.imooc.bootsell.exception.SellException;
import com.imooc.bootsell.service.OrderService;
import com.imooc.bootsell.utils.StaticUtil;
import com.imooc.bootsell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/seller/sellOrder")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = this.orderService.findList(pageRequest);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/order/list");

    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView cancel(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = this.orderService.findOne(orderId);
            this.orderService.cancel(orderDTO);
        } catch (Exception e) {
            log.error("[卖家端取消订单]发生异常{}", e);
            map.put("msg", e);
            map.put("url", "/seller/sellOrder/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/seller/sellOrder/list");
        return new ModelAndView("/common/success", map);

    }

    @RequestMapping(value = "detail", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView detail(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = this.orderService.findOne(orderId);

        } catch (SellException e) {
            log.error("[卖家端查询订单详情]发生异常{}", e);
            map.put("msg", e);
            map.put("url", "/seller/sellOrder/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("/order/detail", map);
    }


    @RequestMapping(value = "/finish", method = RequestMethod.GET, produces = StaticUtil.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView finish(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        try {
            OrderDTO orderDTO = this.orderService.findOne(orderId);
            this.orderService.finish(orderDTO);
        } catch (SellException e) {
            map.put("msg", e);
            map.put("url", "/seller/sellOrder/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "/seller/sellOrder/list");
        return new ModelAndView("/common/success", map);
    }
}
