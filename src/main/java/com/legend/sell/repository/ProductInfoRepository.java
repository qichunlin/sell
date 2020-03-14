package com.legend.sell.repository;

import com.legend.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author legend
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Integer> {

    /**
     * 根据商品状态查询商品
     *
     * @param productStatus
     * @return
     */
    List<ProductInfo> queryByProductStatus(Integer productStatus);

}
