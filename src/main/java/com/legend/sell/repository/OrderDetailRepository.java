package com.legend.sell.repository;

import com.legend.sell.entity.OrderDetail;
import com.legend.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author legend
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 根据订单id查询订单详情
     *
     * @param orderId
     * @return
     */
    List<OrderDetail> queryByOrderId(String orderId);

}
