package com.legend.sell.handler;

import com.legend.sell.config.ProjectUrlConfig;
import com.legend.sell.enums.ResultCodeEnums;
import com.legend.sell.exception.ResponseBankException;
import com.legend.sell.exception.SellException;
import com.legend.sell.exception.SellerAuthorizeException;
import com.legend.sell.utils.ResultVOUtils;
import com.legend.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常捕获
 * SellerAuthorizeException 异常处理
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 拦截登录异常 跳转登录
     * http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
     *
     * @return
     */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login"));
    }

    /**
     * SellException异常处理
     *
     * @return
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e) {
        return ResultVOUtils.error(e.getCode(),e.getMessage());
    }

    /**
     * ResponseBankException异常统一处理
     *
     * @return
     */
    @ResponseStatus(value = HttpStatus.FOUND)
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseBody
    public ResultVO handlerSellException(ResponseBankException e) {
        return ResultVOUtils.error(ResultCodeEnums.ERROR);
    }
}
