package com.imooc.bootsell.service.impl;

import com.imooc.bootsell.config.WeChatAccountConfig;
import com.imooc.bootsell.dto.OrderDTO;
import com.imooc.bootsell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class PushMessageServiceImpl implements PushMessageService {

//    @Autowired
//    private WxMpService wxMpService;

    @Autowired
    private WeChatAccountConfig weChatAccountConfig;


    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(weChatAccountConfig.getTemplateId().get("orderStatus"));
        wxMpTemplateMessage.setToUser(orderDTO.getOrderId());
        List<WxMpTemplateData> wxMpTemplateData = Arrays.asList(
                new WxMpTemplateData("first", "收货"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "15271526358"),
                new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("keyword5", "$" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再来!")
        );
        wxMpTemplateMessage.setData(wxMpTemplateData);
        try{
//            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        }catch (Exception e){
            log.error("[微信模版]发送失败,{}", e);
        }
    }


    public void test(){
        System.out.println("git dev测试");
    }


}
