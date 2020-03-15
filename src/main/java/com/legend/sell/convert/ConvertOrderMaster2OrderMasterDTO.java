package com.legend.sell.convert;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单对象转化为订单传输对象
 *
 * @author legend
 */
public class ConvertOrderMaster2OrderMasterDTO {


    /**
     * 单个对象转换
     *
     * @param orderMaster
     * @return
     */
    public static OrderMasterDTO convert(OrderMaster orderMaster) {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        return orderMasterDTO;
    }

    /**
     * 支持批量转换
     *
     * @param orderMasterList
     * @return
     */
    public static List<OrderMasterDTO> convert(List<OrderMaster> orderMasterList) {
        //第二种方式
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());

        //第一种方式
        /*List<OrderMasterDTO> retOrderMasterDTOList = new ArrayList<>();
        for (OrderMaster orderMaster:orderMasterList) {
            OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
            BeanUtils.copyProperties(orderMaster,orderMasterDTO);
            retOrderMasterDTOList.add(orderMasterDTO);
        }

        return retOrderMasterDTOList;*/
    }
}
