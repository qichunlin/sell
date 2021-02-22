package com.legend.sell.controller;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.service.IOrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/16
 */
@Slf4j
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private IOrderMasterService orderMasterService;

    /**
     * 订单列表
     * http://localhost:8080/sell/seller/order/list?page=2&size=10
     *
     * @param page 第几页开始,从1页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {

        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderMasterDTO> orderDTOPage = orderMasterService.queryAllList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     * http://localhost:8080/sell/seller/order/cancel?orderId=1
     *
     * @param orderId 订单id
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map) {

        try {
            OrderMasterDTO orderDTO = orderMasterService.queryOne(orderId);
            orderMasterService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端订单】不存在！！！{}", e.getMessage());
            map.put("msg", ExceptionCodeEnums.ORDER_NOT_FOUND.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ExceptionCodeEnums.SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }
}
