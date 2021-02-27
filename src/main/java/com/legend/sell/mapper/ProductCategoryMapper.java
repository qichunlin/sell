package com.legend.sell.mapper;

import com.legend.sell.entity.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
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
     * 插入数据
     *
     * @return
     */
    int insertProductCategoryByXml(ProductCategory productCategory);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<ProductCategory> findAllByXml();

    /**
     * 删除数据
     *
     * @param categoryId
     * @return
     */
    @Delete("DELETE FROM `weixin_sell`.`product_category` WHERE `category_id`= #{categoryId, jdbcType=INTEGER}")
    int deleteProductCategory(@Param("categoryId") Integer categoryId);

    /**
     * 更新数据
     *
     * @param categoryId
     * @param categoryName
     * @return
     */
    @Update("UPDATE `weixin_sell`.`product_category` SET `category_name` = #{categoryName, jdbcType=VARCHAR} WHERE `category_id`= #{categoryId, jdbcType=INTEGER}")
    int updateByProductCategory(@Param("categoryId") Integer categoryId, @Param("categoryName") String categoryName);

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
     * 查询数据
     * Results注解是为了将结果映射回去对象 不然对象里面都是null
     *
     * @param categoryName
     * @return
     */
    @Select("select * from `product_category` where category_name=#{categoryName}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_type", property = "categoryType"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<ProductCategory> findProductCategoryByName(String categoryName);

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
