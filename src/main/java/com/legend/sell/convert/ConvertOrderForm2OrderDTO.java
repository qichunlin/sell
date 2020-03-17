package com.legend.sell.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.entity.OrderDetail;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.form.OrderForm;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 买家订单表单转换orderMasterDTO
 *
 * @author legend
 */
@Slf4j
@ToString
public class ConvertOrderForm2OrderDTO {

    /**
     * 转化方法
     *
     * @param orderForm
     * @return
     */
    public static OrderMasterDTO convert(OrderForm orderForm) {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        orderMasterDTO.setBuyerName(orderForm.getName());
        orderMasterDTO.setBuyerPhone(orderForm.getPhone());
        orderMasterDTO.setBuyerAddress(orderForm.getAddress());
        orderMasterDTO.setBuyerOpenid(orderForm.getOpenid());

        //gson转化
        //订单详情
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            //gson转化成list
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象装换】错误，string={}", orderForm.getItems());
            throw new SellException(ExceptionCodeEnums.PARAM_ERROR);
        }

        orderMasterDTO.setOrderDetailList(orderDetailList);

        return orderMasterDTO;
    }

}
