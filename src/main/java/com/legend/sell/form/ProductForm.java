package com.legend.sell.form;

import com.legend.sell.enums.ProductStatusEnums;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 产品表单
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/26
 */
@Data
public class ProductForm {

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品小图
     */
    private String productIcon;

    /**
     * 商品类目
     */
    private Integer categoryType;
}
