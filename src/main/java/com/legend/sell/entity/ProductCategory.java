package com.legend.sell.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目
 *
 * @DynamicUpdate  动态更新
 *
 * @author legend
 */
@Entity
@DynamicUpdate
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    /**
     * 类目id
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 类目类别
     */
    private Integer categoryType;

    /**
     * 创建时间
     **/
    //@JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间
     **/
    //@JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
}
