package com.legend.sell.controller;

import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.exception.ResponseBankException;
import com.legend.sell.exception.SellException;
import com.legend.sell.utils.ResultVOUtils;
import com.legend.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常测试控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
@Controller
@Slf4j
@RequestMapping("/exception")
public class ExceptionController {

    /**
     * 测试抛出ResponseBankException 异常
     * http://localhost:9000/sell/exception/responseException?id=0
     *
     * @param id
     * @return
     */
    @GetMapping("/responseException")
    @ResponseBody
    public ResultVO responseBankException(@RequestParam("id") Integer id) {
        if (id == 0) {
            throw new ResponseBankException("44444");
        }
        return ResultVOUtils.success("访问成功");
    }

    /**
     * 测试抛出SellException 异常
     * http://localhost:9000/sell/exception/sellException?id=0
     *
     * @param id
     * @return
     */
    @GetMapping("/sellException")
    @ResponseBody
    public ResultVO SellException(@RequestParam("id") Integer id) {
        if (id == 0) {
            throw new SellException(ExceptionCodeEnums.ORDER_NOT_FOUND);
        }
        return ResultVOUtils.success("访问成功1111");
    }
}
