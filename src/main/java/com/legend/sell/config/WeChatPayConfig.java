package com.legend.sell.config;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置
 *
 * @author legend
 */
@Component
public class WeChatPayConfig {

    @Autowired
    private WeChatAccountConfig weChatAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayService() {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        //通过Bean的方法中获取支付配置
        bestPayService.setWxPayConfig(wxPayConfig());

        return bestPayService;
    }


    @Bean
    public WxPayConfig wxPayConfig() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(weChatAccountConfig.getMpAppId());
        wxPayConfig.setAppSecret(weChatAccountConfig.getMpAppSecret());
        wxPayConfig.setKeyPath(weChatAccountConfig.getKeyPath());
        wxPayConfig.setMchKey(weChatAccountConfig.getMchKey());
        wxPayConfig.setNotifyUrl(wxPayConfig.getNotifyUrl());
        //wxPayConfig.setSslContext(weChatAccountConfig.getSslContext());

        return wxPayConfig;
    }
}
