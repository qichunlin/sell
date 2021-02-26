package com.legend.sell.form;

import lombok.Data;

/**
 * 分类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/26
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /**
     * 类目名字.
     */
    private String categoryName;

    /**
     * 类目编号.
     */
    private Integer categoryType;
}
