package com.legend.sell.controller;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.entity.ProductCategory;
import com.legend.sell.entity.ProductInfo;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.service.IProductCategoryService;
import com.legend.sell.service.IProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/25
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private IProductInfoService productInfoService;
    @Autowired
    private IProductCategoryService productCategoryService;

    /**
     * 商品展示和新增
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(required = false, value = "productId") Integer productId,
                                Map<String, Object> map) {
        try {
            if (!StringUtils.isEmpty(productId)) {
                ProductInfo productInfo = productInfoService.queryOne(productId);
                map.put("productInfo", productInfo);
            }

            //查询所有类目
            List<ProductCategory> categoryList = productCategoryService.queryAll();
            map.put("categoryList", categoryList);
        } catch (SellException e) {
            map.put("msg", ExceptionCodeEnums.PRODUCT_NOT_FOUND.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        return new ModelAndView("product/index", map);
    }

    /**
     * 商品下架
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/offSale/{productId}")
    public ModelAndView offSale(@PathVariable("productId") Integer productId,
                               Map<String, Object> map) {
        try {
            productInfoService.offSale(productId);
        } catch (SellException e) {
            map.put("msg", ExceptionCodeEnums.ORDER_NOT_FOUND.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ExceptionCodeEnums.SUCCESS.getMessage());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    /**
     * 商品上架
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/onSale/{productId}")
    public ModelAndView onSale(@PathVariable("productId") Integer productId,
                               Map<String, Object> map) {
        try {
            productInfoService.onSale(productId);
        } catch (SellException e) {
            map.put("msg", ExceptionCodeEnums.ORDER_NOT_FOUND.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ExceptionCodeEnums.SUCCESS.getMessage());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    /**
     * 商品列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.queryByPage(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }
}
