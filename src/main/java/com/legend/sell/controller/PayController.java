package com.legend.sell.controller;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.service.IOrderMasterService;
import com.legend.sell.service.IPayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 支付
 *
 * @author legend
 */
@Controller
public class PayController {

    @Autowired
    private IOrderMasterService orderMasterService;

    @Autowired
    private IPayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
        //1. 查询订单
        OrderMasterDTO orderMasterDTO = orderMasterService.queryOne(orderId);
        if (orderMasterDTO == null) {
            throw new SellException(ExceptionCodeEnums.ORDER_NOT_FOUND);
        }

        //2. 发起支付
        PayResponse payResponse = payService.create(orderMasterDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create", map);
    }


    /**
     * 微信异步通知
     *
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
