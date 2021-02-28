package com.legend.sell.service.impl;

import com.legend.sell.convert.ConvertOrderMaster2OrderMasterDTO;
import com.legend.sell.dto.CartDTO;
import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.entity.OrderDetail;
import com.legend.sell.entity.OrderMaster;
import com.legend.sell.entity.ProductInfo;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.enums.OrderStatusEnums;
import com.legend.sell.enums.PayStatusEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.repository.OrderDetailRepository;
import com.legend.sell.repository.OrderMasterRepository;
import com.legend.sell.service.IOrderMasterService;
import com.legend.sell.service.IPayService;
import com.legend.sell.service.IProductInfoService;
import com.legend.sell.service.IPushMessageService;
import com.legend.sell.utils.KeyUtils;
import com.legend.sell.ws.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author legend
 */
@Slf4j
@Service
public class OrderMasterServiceImpl implements IOrderMasterService {

    @Autowired
    private IProductInfoService productInfoService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private IPushMessageService pushMessageService;
    @Autowired
    private IPayService payService;
    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMasterDTO create(OrderMasterDTO orderMasterDTO) {
        //记得需要进行事务控制
        //整个订单创建的时候就已经生成订单id
        String orderId = KeyUtils.getUniqueKey();

        //总价的值
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //第一种方式获取购物车数据
        List<CartDTO> cartDTOList = new ArrayList<>();

        //1.查询商品(数量/价格)
        for (OrderDetail orderDetail : orderMasterDTO.getOrderDetailList()) {
            //根据商品id获取商品信息
            ProductInfo productInfo = productInfoService.queryOne(Integer.parseInt(orderDetail.getProductId()));
            if (productInfo == null) {
                //抛异常:商品不存在
                throw new SellException(ExceptionCodeEnums.PRODUCT_NOT_FOUND);
            }

            //2.计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情写入数据库
            orderDetail.setDetailId(KeyUtils.getUniqueKey());
            orderDetail.setOrderId(orderId);
            //使用对象拷贝赋值相同属性值
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);

            //第一次种获取购物车数据的方法
            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }

        //3.写入订单数据库(OrderMaster/OrderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDTO.setOrderId(orderId);
        //这里需要注意顺序(属性值为null的也会被拷贝)
        //使用对象拷贝赋值相同属性值
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        //这里需要重新把 orderStatus和payStatus重新赋值
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.NEW.getCode());
        //保存或者更新
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        //第二种方式获取购物车数据(推荐)
        List<CartDTO> cartDTOList2 =
                orderMasterDTO.getOrderDetailList().stream()
                        .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                        .collect(Collectors.toList());
        //执行减少操作
        productInfoService.decreaseStock(cartDTOList2);

        //发送websocket消息
        webSocketServer.sendMessage("有新的订单");
        return orderMasterDTO;
    }

    @Override
    public OrderMasterDTO queryOne(String orderId) {
        //创建查询对象
        OrderMaster queryOrderMaster = new OrderMaster();
        queryOrderMaster.setOrderId(orderId);
        Example<OrderMaster> masterExample = Example.of(queryOrderMaster);
        //获取订单信息
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findOne(masterExample);
        OrderMaster orderMaster;
        //判断对象是否存在
        if (orderMasterOptional.isPresent()) {
            orderMaster = orderMasterOptional.get();
        } else {
            throw new SellException(ExceptionCodeEnums.ORDER_NOT_FOUND);
        }

        //根据订单id查询对应的订单详情
        List<OrderDetail> orderDetailsList = orderDetailRepository.queryByOrderId(orderId);

        if (CollectionUtils.isEmpty(orderDetailsList)) {
            throw new SellException(ExceptionCodeEnums.ORDER_DETAIL_NOT_FOUND);
        }

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        orderMasterDTO.setOrderDetailList(orderDetailsList);

        return orderMasterDTO;
    }

    @Override
    public Page<OrderMasterDTO> queryListByBuyerOpenId(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.queryByBuyerOpenid(buyerOpenid, pageable);
        //转化器调用(OrderMaster---->OrderMasterDTO)
        List<OrderMasterDTO> orderMasterDTOS = ConvertOrderMaster2OrderMasterDTO.convert(orderMasterPage.getContent());

        Page<OrderMasterDTO> orderMasterDTOPage = new PageImpl<>(orderMasterDTOS);

        return orderMasterDTOPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);

        //判断订单状态
        if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("[取消订单]订单状态不正确,orderId={},orderStatus={}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ExceptionCodeEnums.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        OrderMaster orderMasterUpdate = orderMasterRepository.save(orderMaster);
        if (orderMasterUpdate == null) {
            log.error("[取消订单]更新失败,orderMaster={}", orderMasterUpdate);
            throw new SellException(ExceptionCodeEnums.ORDER_STATUS_UPDATE_FAIL);
        }

        //返还库存
        if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailList())) {
            log.error("[取消订单]订单中无订单详情,orderMasterDTO={}", orderMasterDTO);
            throw new SellException(ExceptionCodeEnums.ORDER_DETAIL_NOT_FOUND);
        }
        //构造购物车对象
        List<CartDTO> cartDTOList = orderMasterDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());

        //增加库存
        productInfoService.increaseStock(cartDTOList);

        //如果已支付,需要退款
        if (orderMasterDTO.getPayStatus().equals(PayStatusEnums.FINISH.getCode())) {
            //调用退款
            payService.refund(orderMasterDTO);
        }
        return orderMasterDTO;
    }

    @Override
    public OrderMasterDTO finish(OrderMasterDTO orderMasterDTO) {
        //判断订单状态
        if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("[完结订单]..订单状态不正确,orderId={}..orderStatus...{}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ExceptionCodeEnums.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderMasterDTO.setOrderStatus(OrderStatusEnums.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("[完结订单]更新失败,orderMaster={}", updateResult);
            throw new SellException(ExceptionCodeEnums.ORDER_STATUS_UPDATE_FAIL);
        }

        //推送微信模板信息
        pushMessageService.pushOrderStatusNotice(orderMasterDTO);

        return orderMasterDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMasterDTO pay(OrderMasterDTO orderMasterDTO) {
        //判断订单状态
        if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("[订单支付完成]..订单状态不正确,orderId={}..orderStatus...{}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ExceptionCodeEnums.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderMasterDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())) {
            log.error("[订单支付完成]..订单支付状态不正确,orderId={}..orderStatus...{}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ExceptionCodeEnums.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderMasterDTO.setPayStatus(PayStatusEnums.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("[订单支付完成]更新失败,orderMaster={}", updateResult);
            throw new SellException(ExceptionCodeEnums.ORDER_STATUS_UPDATE_FAIL);
        }
        return orderMasterDTO;
    }

    @Override
    public Page<OrderMasterDTO> queryAllList(Pageable pageable) {
        //1.查找所有订单
        Page<OrderMaster> orderMasterList = orderMasterRepository.findAll(pageable);
        //转化成DTO
        List<OrderMasterDTO> orderDtoList = ConvertOrderMaster2OrderMasterDTO.convert(orderMasterList.getContent());
        return new PageImpl<>(orderDtoList, pageable, orderMasterList.getTotalElements());
    }
}
