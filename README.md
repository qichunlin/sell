# weixin_sell
使用springboot+vue构建的微信点餐系统


## 微信支付模块

### 微信网页授权

#### 官方文档
- https://mp.weixin.qq.com/wiki

- 新版的
https://pay.weixin.qq.com/wiki/doc/api/index.html

https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_1

#### 调试
- https://natapp.cn


#### 第三方SDK
- https://github.com/Wechat-Group/weixin-java-tools



#### 手动获取OPENID小结
- 第一步  设置域名(其实是微信的白名单校验)
通过内网穿透的工具调试(natapp.cn)
还有一个校验文件需要放到网站的根路径下面(resource/static)


- 2 第二步：用户同意授权，获取code  (snsapi_base几乎是用户无感知的拿到的信息比较少,snsapi_userinfo会出现弹窗通过用户来确认对应的能获取的更多信息)
https://open.weixin.qq.com/connect/oauth2/
authorize?appid=wx520c15f417810387&redirect_uri=https%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wechat_redirect


- 3 第三步：通过code换取网页授权access_token
```
https://api.weixin.qq.com/sns/oauth2/
access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
```

`正确时返回的JSON数据包如下`
```
{
  "access_token":"ACCESS_TOKEN",
  "expires_in":7200,
  "refresh_token":"REFRESH_TOKEN",
  "openid":"OPENID",
  "scope":"SCOPE" 
}

```

- 4 拉取等等



- 5 完整的
```
获取基本信息
https://open.weixin.qq.com/connect/oauth2/
authorize?appid=wxd898fcb01713c658&redirect_uri=http://sell.natapp4.cc/sell/weixin/
auth&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect 


http://sell.natapp4.cc/se11/weixin/auth/?code=abcdcacas 


https://api.weixin.qq.com/sns/oauth2/
access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code



{
    "access_token":"ZYQbeRArAY1HXZQNyHDV4nWN6m6PGq4ZsUsbX5CQGoeX8TgV_JtfMACSIKHXKWAdqpS-98DjNr0iPWGN9w6LYeazuTD5Ezwhg5kZWpd0S7s",
    "expires_in":7200,
    "refresh_token":"Pc8Rrk6POHRHWDyR-SyXVGPhZamBfsIv-zDigy3KsQC9ZW-3-NYCmEZFpyEMQ--qIKmMDWKo8DXN-VVSMCpg5mHW58D Y1NqQ_Q-DGL8iTC0",
    "openid":"oTgZpweNnfiVA9ER9EIXoH-j1rw0",
    "scope":"snsapi_base"
}


openid:oTgZpweNnfiVA9ER9EIXoH-jLrwQ


-----------------

获取user_info
https://open weixin.qq. com/connect/oauth2/authorize?
appid=wxd898fcb01713c658&redirect_uri=http:/sell.natapn4.cc/sell/weixin/
auth& response_type=code&scope=snsapi_userinfo&state=STATE#wechat_direct

```


#### 使用sdk的方式---Github提供开源做好的微信接口封装
https://github.com/chanjarster/weixin-java-tools

https://github.com/Wechat-Group/WxJava

```pom.xml
<!--微信公众号集成工具-->
<dependency>
    <groupId>com.github.binarywang</groupId>
    <artifactId>weixin-java-mp</artifactId>
    <version>2.8.0</version>
</dependency>
```

`快速获取实现类的快捷键:ctrl+alt+b`


### 微信支付
![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200322141332288-1412655356.png)


最好的支付SDK
https://github.com/Pay-Group/best-pay-sdk


![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200323064500789-1813193533.png)



## Swagger2接口文档地址
```
http://127.0.0.1:8080/sell/swagger-ui.html
```


## 修改nginx的配置代理信息
- 修改为电脑的IP地址
![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200313220553288-208526422.png)

- 可以配置一下域名访问
![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200313220822228-1215622538.png)


- 重启nginx
`nginx -s reload`
![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200313220624901-966618668.png)


## 如何在浏览器中添加一个cookie参数

![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200313215952453-1470479590.png)


- 添加参数`document.cookie='openid=123'`
`打开访问 http://192.168.0.115/#/order`
![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200313220143701-2127007982.png)

![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200313220040684-2050416951.png)

![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200313220310944-205919795.png)

![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200313220404016-794162081.png)
![](https://img2020.cnblogs.com/blog/1231979/202003/1231979-20200313220433203-1903780249.png)
