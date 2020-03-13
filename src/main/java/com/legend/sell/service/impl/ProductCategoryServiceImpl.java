package com.legend.sell.service.impl;

import com.legend.sell.entity.ProductCategory;
import com.legend.sell.repository.ProductCategoryRepository;
import com.legend.sell.service.IProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author legend
 */
@Slf4j
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Override
    public ProductCategory queryOne(Integer id) {
        Optional<ProductCategory> temp = categoryRepository.findById(id);
        ProductCategory productCategory = temp.get();
        return productCategory;
    }

    @Override
    public List<ProductCategory> queryAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> queryByCategoryTypeIn(List<Integer> categoryTypes) {
        return categoryRepository.queryByCategoryTypeIn(categoryTypes);
    }

    @Override
    public ProductCategory save(ProductCategory category) {
        return categoryRepository.save(category);
    }
}
