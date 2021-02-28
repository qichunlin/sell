<html>
<#--<head>
    <meta charset="UTF-8">
    <title>卖家商品列表</title>
    <!-- Bootstrap &ndash;&gt;
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

</head>-->
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>
                            订单id
                        </th>
                        <th>
                            买家姓名
                        </th>
                        <th>
                            交付时间
                        </th>
                        <th>
                            买家手机号码
                        </th>
                        <th>
                            买家微信openid
                        </th>
                        <th>
                            订单总额
                        </th>
                        <th>
                            订单状态
                        </th>
                        <th>
                            订单状态2
                        </th>
                        <th>
                            支付状态
                        </th>
                        <th>
                            支付状态2
                        </th>
                        <th>
                            创建时间
                        </th>
                        <th colspan="2">
                            操作
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list orderDTOPage.content as orderDTO >
                        <tr>
                            <td>
                                ${orderDTO.orderId}
                            </td>
                            <td>
                                ${orderDTO.buyerName}
                            </td>
                            <td>
                                ${orderDTO.buyerPhone}
                            </td>
                            <td>
                                ${orderDTO.buyerAddress}
                            </td>
                            <td>
                                ${orderDTO.buyerOpenid}
                            </td>
                            <td>
                                ${orderDTO.orderAmount}
                            </td>
                            <td>
                                ${orderDTO.orderStatus}
                            </td>
                            <td>
                                ${orderDTO.getOrderStatusEnum().msg}
                            </td>
                            <td>
                                ${orderDTO.payStatus}
                            </td>
                            <td>
                                ${orderDTO.getPayStatusEnum().msg}
                            </td>
                            <td>
                                ${orderDTO.createTime}
                            </td>
                            <td>
                                <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                            </td>
                            <td>
                                <#if orderDTO.getOrderStatus() == 0>
                                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                </#if>
                            </td>

                        </tr>
                    </#list>


                    </tbody>
                </table>
            </div>

            <div class="span12">
                <ul class="pagination pull-right">
                    <#--<#if currentPage == 1>-->
                    <#--lte是小于等于-->
                    <#if currentPage lte 1>
                        <li class="disabled">
                            <a href="#">上一页</a>
                        </li>
                    <#else>
                        <li>
                            <a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a>
                        </li>
                    </#if>

                    <#--0 到3-->
                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#--<#if currentPage == orderDTOPage.getTotalPages()>-->
                    <#--gte 大于等于-->
                    <#if currentPage gte orderDTOPage.getTotalPages()>
                        <li class="disabled">
                            <a href="#">下一页</a>
                        </li>
                    <#else>
                        <li>
                            <a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a>
                        </li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>
<#--弹窗-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                你有新的订单
            </div>
            <div class="modal-footer">
                <#--关闭窗口 音乐停止-->
                <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
            </div>
        </div>
    </div>
</div>

<#--播放音乐-->
<audio id="notice" loop="loop">
    <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
</audio>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>

    var websocket = null;

    //判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:9000/sell/webSocket");
    } else {
        alert('Not support websocket')
    }

    websocket.onopen = function (event) {
        console.log('建立连接');
    };
    websocket.onclose = function (event) {
        console.log('连接关闭');
    }

    websocket.onmessage = function (event) {
        console.log('收到消息：' + event.data);
        //弹窗提醒, 播放音乐
        $('#myModal').modal('show');

        document.getElementById('notice').play();
    }

    websocket.onerror = function (event) {
        console.log('websocket通信发生错误');
        alert("websocket通信发生错误!!!");
    }

    window.onbeforeunload = function () {
        websocket.close();
    }
</script>


</body>
</html>

<#--
<h1>list</h1>
<h1>${orderDTOPage.getTotalPages()}</h1>
<h1>${orderDTOPage.totalPages}</h1>

<#list orderDTOPage.content as orderDTO >
    ${orderDTO.orderId}  <br>
</#list>-->
