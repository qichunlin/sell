package com.legend.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 买家订单前台表单数据
 *
 * @author legend
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotEmpty(message = "电话不能为空")
    private String phone;

    @NotEmpty(message = "地址不能为空")
    private String address;

    @NotEmpty(message = "openid不能为空")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;

}
