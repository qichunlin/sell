package com.legend.sell.repository;

import com.legend.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author legend
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    /**
     * 查询目录类型
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> queryByCategoryTypeIn(List<Integer> categoryTypeList);

}
