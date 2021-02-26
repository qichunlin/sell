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
                            商品id
                        </th>
                        <th>
                            商品名称
                        </th>
                        <th>
                            商品单价
                        </th>
                        <th>
                            商品库存
                        </th>
                        <th>
                            商品描述
                        </th>
                        <th>
                            商品小图
                        </th>
                        <th>
                            商品类目
                        </th>
                        <th>
                            商品状态
                        </th>
                        <th>
                            创建时间
                        </th>
                        <th>
                            更新时间
                        </th>
                        <th colspan="2">
                            操作
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list productInfoPage.content as productInfo >
                        <tr>
                            <td>
                                ${productInfo.productId}
                            </td>
                            <td>
                                ${productInfo.productName}
                            </td>
                            <td>
                                ${productInfo.productPrice}
                            </td>
                            <td>
                                ${productInfo.productStock}
                            </td>
                            <td>
                                ${productInfo.productDescription}
                            </td>
                            <td>
                                <img src="${productInfo.productIcon}" align="" height="100" width="100">
                            </td>
                            <td>
                                ${productInfo.categoryType}
                            </td>
                            <td>
                                ${productInfo.getProductStatusEnums().message!""}
                            </td>
                            <td>
                                ${productInfo.createTime}
                            </td>
                            <td>
                                ${productInfo.updateTime}
                            </td>
                            <td>
                                <a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a>
                            </td>
                            <td>
                                <#if productInfo.getProductStatus() == 0>
                                    <a href="/sell/seller/product/onSale/${productInfo.productId}">上架</a>
                                </#if>
                                <#if productInfo.getProductStatus() == 1>
                                    <a href="/sell/seller/product/offSale/${productInfo.productId}">下架</a>
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
                            <a href="/sell/seller/product/list?page=${currentPage - 1}&size=${size}">上一页</a>
                        </li>
                    </#if>

                    <#--0 到3-->
                    <#list 1..productInfoPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#--<#if currentPage == orderDTOPage.getTotalPages()>-->
                    <#--gte 大于等于-->
                    <#if currentPage gte productInfoPage.getTotalPages()>
                        <li class="disabled">
                            <a href="#">下一页</a>
                        </li>
                    <#else>
                        <li>
                            <a href="/sell/seller/product/list?page=${currentPage + 1}&size=${size}">下一页</a>
                        </li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>