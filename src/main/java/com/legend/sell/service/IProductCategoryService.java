package com.legend.sell.service;


import com.legend.sell.entity.ProductCategory;

import java.util.List;

/**
 * 商品分类service
 *
 * @author legend
 */
public interface IProductCategoryService {

    ProductCategory queryOne(Integer id);

    List<ProductCategory> queryAll();

    List<ProductCategory> queryByCategoryTypeIn(List<Integer> categoryTypes);

    ProductCategory save(ProductCategory category);

}
