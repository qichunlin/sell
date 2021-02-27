package com.legend.sell.mapper;

import com.legend.sell.entity.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * mybatis注解方式
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
@Mapper
public interface ProductCategoryMapper {

    /**
     * 查询数据
     * Results注解是为了将结果映射回去对象 不然对象里面都是null
     *
     * @param categoryType
     * @return
     */
    @Select("select * from `product_category` where category_type=#{categoryType}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_type", property = "categoryType"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    ProductCategory findProductCategory(Integer categoryType);

    /**
     * 插入数据通过对象
     *
     * @param productCategory
     * @return
     */
    @Insert("INSERT INTO `weixin_sell`.`product_category`(`category_name`, `category_type`) VALUES (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
    int insertByProductCategory(ProductCategory productCategory);

    /**
     * 插入数据通过map
     *
     * @param map
     * @return
     */
    @Insert("INSERT INTO `weixin_sell`.`product_category`(`category_name`, `category_type`) VALUES (#{category_name, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER})")
    int insertProductCategoryByMap(Map<String, Object> map);
}
