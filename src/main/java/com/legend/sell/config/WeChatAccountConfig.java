package com.legend.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;

/**
 * 微信账号配置(为了读取application.yml配置文件中的属性参数)
 *
 * @author legend
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatAccountConfig {
    /**
     * 公众平台appid
     */
    private String mpAppId;

    /**
     * 公众平台secret
     */
    private String mpAppSecret;

    /**
     * 开放平台appid
     */
    private String openAppId;

    /**
     * 开放平台secret
     */
    private String openAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 微信支付异步通知地址
     */
    private String notifyUrl;

    private SSLContext sslContext;
}
