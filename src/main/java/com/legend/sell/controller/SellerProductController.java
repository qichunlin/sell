package com.legend.sell.controller;

import com.legend.sell.entity.ProductCategory;
import com.legend.sell.entity.ProductInfo;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.form.ProductForm;
import com.legend.sell.service.IProductCategoryService;
import com.legend.sell.service.IProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
     * 商品保存/更新
     * CachePut注解在方法每一次执行完都会将最新的数据保存到redis
     *
     * @param productForm
     * @param map
     * @return
     */
    @PostMapping("/save")
    @CachePut(cacheNames = "product", key = "123")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", ExceptionCodeEnums.PRODUCT_NOT_FOUND.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("/common/error", map);
        }

        try {
            ProductInfo productInfo = new ProductInfo();
            if (!StringUtils.isEmpty(productForm.getProductId())) {
                //数据拷贝
                productInfo = productInfoService.queryOne(productForm.getProductId());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            productInfoService.save(productInfo);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

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
