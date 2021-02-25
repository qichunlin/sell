package com.legend.sell.service;

import com.legend.sell.dto.CartDTO;
import com.legend.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 *
 * @author legend
 */
public interface IProductInfoService {

    /**
     * 根据id查询一条记录
     *
     * @param id
     * @return
     */
    ProductInfo queryOne(Integer id);

    /**
     * 查询在架商品
     *
     * @return
     */
    List<ProductInfo> queryUpAll();

    /**
     * 查询商品分页
     *
     * @param pageable
     * @return ProductInfo
     */
    Page<ProductInfo> queryByPage(Pageable pageable);

    /**
     * 保存商品
     *
     * @param productInfo
     * @return ProductInfo
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 减少库存(传入一个购物车对象)
     *
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 增加库存
     *
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 商品下架
     *
     * @param productId
     * @return ProductInfo
     */
    ProductInfo offSale(Integer productId);

    /**
     * 商品上架
     *
     * @param productId
     * @return ProductInfo
     */
    ProductInfo onSale(Integer productId);
}
