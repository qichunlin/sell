package com.legend.sell.controller;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.service.IOrderMasterService;
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
}
