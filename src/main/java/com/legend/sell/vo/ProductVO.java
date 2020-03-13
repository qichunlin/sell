package com.legend.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 返回给前端的商品详情（包含类目）
 *
 * @author legend
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -4471285242600830239L;
    /**
     * 原来页面的要求直接是name 但是我们不能直接定义name,易混淆,所以开发通过属性名转换来标识
     * 第一层是类目的名字
     */
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfos;

}
