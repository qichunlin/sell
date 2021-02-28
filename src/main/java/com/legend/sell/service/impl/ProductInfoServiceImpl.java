package com.legend.sell.service.impl;

import com.legend.sell.dto.CartDTO;
import com.legend.sell.entity.ProductInfo;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.enums.ProductStatusEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.repository.ProductInfoRepository;
import com.legend.sell.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author legend
 */
@Service
@CacheConfig(cacheNames = "productInfo")
public class ProductInfoServiceImpl implements IProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

//    @Autowired
//    private SellerProductMapper sellerProductMapper;

    @Override
    //@Cacheable(cacheNames = "productInfo", key = "123")
    @Cacheable(key = "123")
    public ProductInfo queryOne(Integer id) {
        return productInfoRepository.findById(id).get();
    }

    @Override
    public List<ProductInfo> queryUpAll() {
        return productInfoRepository.queryByProductStatus(ProductStatusEnums.UP.getCode());
    }

    @Override
    public Page<ProductInfo> queryByPage(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    //@CachePut(cacheNames = "productInfo",key = "123")
    @CachePut(key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        //使用mybatis
//        sellerProductMapper.insertByObject(productInfo);
//        sellerProductMapper.updateByObject(productInfo);
//        return new ProductInfo();
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void decreaseStock(List<CartDTO> cartDTOList) {
        //遍历购物车数据
        for (CartDTO cartDTO : cartDTOList) {
            //新版本的Jpa中使用findOne方法
            ProductInfo queryProductInfo = new ProductInfo();
            queryProductInfo.setProductId(Integer.parseInt(cartDTO.getProductId()));
            Example<ProductInfo> example = Example.of(queryProductInfo);
            //查找商品
            Optional<ProductInfo> productOptional = productInfoRepository.findOne(example);
            //判断处理
            /*if (productOptional.isPresent()) {
                ProductInfo productInfo = productOptional.get();
                System.out.println(productInfo.toString());
            } else {
                // handle not found, return null or throw
                System.out.println("no exit!");
            }*/

            ProductInfo productInfo = productOptional.get();
            if (null == productInfo) {
                //抛异常:未找到商品
                throw new SellException(ExceptionCodeEnums.PRODUCT_NOT_FOUND);
            }
            //商品的库存 - 购物车的数量
            Integer stock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (stock < 0) {
                //抛异常:商品库存不足
                throw new SellException(ExceptionCodeEnums.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);

        }

    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO : cartDTOList) {
            //新版本的Jpa中使用findOne方法
            ProductInfo queryProductInfo = new ProductInfo();
            queryProductInfo.setProductId(Integer.parseInt(cartDTO.getProductId()));
            Example<ProductInfo> example = Example.of(queryProductInfo);
            //查找商品
            ProductInfo productInfo = productInfoRepository.findOne(example).get();
            if (null == productInfo) {
                throw new SellException(ExceptionCodeEnums.PRODUCT_NOT_FOUND);
            }
            //商品的库存 - 购物车的数量
            Integer stock = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo offSale(Integer productId) {
        ProductInfo productInfo = productInfoRepository.findById(productId).get();
        if (productInfo == null) {
            throw new SellException(ExceptionCodeEnums.PRODUCT_NOT_EXIST);
        }
        //1.判断商品状态
        if (ProductStatusEnums.DOWN == productInfo.getProductStatusEnums()) {
            throw new SellException(ExceptionCodeEnums.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnums.DOWN.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo onSale(Integer productId) {
        ProductInfo productInfo = productInfoRepository.findById(productId).get();
        if (productInfo == null) {
            throw new SellException(ExceptionCodeEnums.PRODUCT_NOT_EXIST);
        }
        //1.判断商品状态
        if (ProductStatusEnums.UP == productInfo.getProductStatusEnums()) {
            throw new SellException(ExceptionCodeEnums.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());
        return productInfoRepository.save(productInfo);
    }
}
