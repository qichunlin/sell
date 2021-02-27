package com.legend.sell.service.impl;

import com.legend.sell.config.WeChatAccountConfig;
import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.service.IPushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements IPushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WeChatAccountConfig accountConfig;

    @Override
    public void pushOrderStatusNotice(OrderMasterDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        //templateMessage.setTemplateId("g_01uPNUjtCEMW-ouzIv06ZZuAQmn3u2u9_3liz7WNg");
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        templateMessage.setToUser(orderDTO.getBuyerOpenid());

        //模板中的参数变量值
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货。"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "15607323445"),
                new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临！")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模版消息】发送失败, {}", e);
        }
    }
}
