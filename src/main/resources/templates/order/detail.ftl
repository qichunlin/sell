<html>
<head>
    <meta charset="UTF-8">
    <title>卖家商品详情</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <address> <h2>订单详情</h2><br /> <h3>订单id：${orderId}</h3> <br />
            <table class="table">
                <thead>
                <tr>
                    <th>
                        订单编号
                    </th>
                    <th>
                        订单详情id
                    </th>
                    <th>
                        商品价格
                    </th>
                    <th>
                        商品图标
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list orderDetailList as orderDetail>
                    <tr>
                        <td>
                            ${orderDetail.orderId}
                        </td>
                        <td>
                            ${orderDetail.detailId}
                        </td>
                        <td>
                            ${orderDetail.productPrice}
                        </td>
                        <td>
                            <img src="${orderDetail.productIcon}" width="150" height="150">
                        </td>
                    </tr>
                </#list>

                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>