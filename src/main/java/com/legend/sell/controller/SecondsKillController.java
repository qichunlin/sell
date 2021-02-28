package com.legend.sell.controller;

import com.legend.sell.service.ISecondsKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 秒杀控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/28
 */
@Controller
@RequestMapping("/skill")
@Slf4j
public class SecondsKillController {

    @Autowired
    private ISecondsKillService secondsKillService;

    /**
     * 查询秒杀特价商品信息
     *
     * @param productId
     * @return
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable("productId") String productId) {
        return secondsKillService.querySecondsKillProductInfo(productId);
    }

    /**
     * 秒杀 没有抢到则报错xxxx
     *
     * @param productId
     * @return
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable("productId") String productId) {
        log.info("@skill request, productId:{}", productId);

        //秒杀的方法
        secondsKillService.orderProductMockDiffUser(productId);
        return secondsKillService.querySecondsKillProductInfo(productId);
    }
}
