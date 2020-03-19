package com.legend.sell.controller;

import com.legend.sell.convert.ConvertOrderForm2OrderDTO;
import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.form.OrderForm;
import com.legend.sell.service.IBuyerOrderService;
import com.legend.sell.service.IOrderMasterService;
import com.legend.sell.utils.ResultVOUtils;
import com.legend.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家订单
 *
 * @author legend
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private IOrderMasterService orderMasterService;

    @Autowired
    private IBuyerOrderService buyerOrderService;

    /**
     * 创建订单
     *
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            //表单验证不通过
            log.error("【创建订单】订单数据不正确，orderForm={}", orderForm);

            //抛出异常显示那个属性参数信息不对(@NotEmpty上面的message消息内容)
            throw new SellException(ExceptionCodeEnums.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //form表单转化成OrderMasterDTO对象
        OrderMasterDTO orderDTO = ConvertOrderForm2OrderDTO.convert(orderForm);

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空，orderDTO={}", orderDTO);
            throw new SellException(ExceptionCodeEnums.Order_CART_EMPTY);
        }
        //创建订单
        OrderMasterDTO result = orderMasterService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());

        return ResultVOUtils.success(map);
    }

    /**
     * 订单列表
     *
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderMasterDTO>> list(@RequestParam("openid") String openid,
                                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单列表]openid为空");
            throw new SellException(ExceptionCodeEnums.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page, size);
        //查询订单
        Page<OrderMasterDTO> orderMasterDTOList = orderMasterService.queryList(openid, pageRequest);
        return ResultVOUtils.success(orderMasterDTOList.getContent());
    }

    /**
     * 订单详情
     *
     * @param openid
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVO<OrderMasterDTO> detail(@RequestParam("openid") String openid,
                                           @RequestParam("orderId") String orderId) {
        return ResultVOUtils.success(buyerOrderService.queryOrderOne(openid, orderId));
    }

    /**
     * 取消订单
     *
     * @param openid
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResultVO<OrderMasterDTO> cancel(@RequestParam("openid") String openid,
                                           @RequestParam("orderId") String orderId) {

        return ResultVOUtils.success(buyerOrderService.cancel(openid, orderId));
    }

}
