# weixin_sell
使用springboot+vue构建的微信点餐系统


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
