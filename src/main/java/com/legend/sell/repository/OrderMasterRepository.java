package com.legend.sell.repository;

import com.legend.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author legend
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /**
     * 6-2
     * 按照买家的openid查询订单
     *
     * @param buyerOpenid
     * @param pageable    分页  不然数据量大
     * @return
     */
    Page<OrderMaster> queryByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
