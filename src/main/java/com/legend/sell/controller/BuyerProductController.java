package com.legend.sell.controller;

import com.legend.sell.entity.ProductCategory;
import com.legend.sell.entity.ProductInfo;
import com.legend.sell.service.IProductCategoryService;
import com.legend.sell.service.IProductInfoService;
import com.legend.sell.utils.ResultVOUtils;
import com.legend.sell.vo.ProductInfoVO;
import com.legend.sell.vo.ProductVO;
import com.legend.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品控制器
 *
 * @author legend
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private IProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list() {
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList = productInfoService.queryUpAll();

        //2.查询类目(一次查询)
        List<Integer> categoryTypeList = new ArrayList<>();
        //传统方法
        for (ProductInfo pro : productInfoList) {
            categoryTypeList.add(pro.getCategoryType());
        }

        //精简方法(Java8,Lambda表达式)
        List<Integer> categoryTypeList2 = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        //查询类目根据Type
        List<ProductCategory> productCategoriesList = productCategoryService.queryByCategoryTypeIn(categoryTypeList2);

        //3.数据拼装
        //存储每一种商品
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoriesList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            //存储每一种商品类目下面的商品信息对象
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productCategory.getCategoryType().equals(productInfo.getCategoryType())) {
                    //创建商品信息值对象
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //对象拷贝
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfos(productInfoVOList);
            productVOList.add(productVO);
        }
        //结果值对象(优化)
        /*esultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("hello");
        resultVO.setData(productVOList);*/
        return ResultVOUtils.success(productVOList);
    }
}
