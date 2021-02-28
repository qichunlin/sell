package com.legend.sell.service.impl;

import com.legend.sell.exception.SellException;
import com.legend.sell.redis.RedisLock;
import com.legend.sell.service.ISecondsKillService;
import com.legend.sell.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2021/2/28
 */
@Service
public class SecondsKillServiceImpl implements ISecondsKillService {

    /**
     * 超时时间 10s
     */
    private static final int TIMEOUT = 10 * 1000;

    @Autowired
    private RedisLock redisLock;

    /**
     * 国庆活动,皮蛋瘦肉粥特价,限量100000份
     */
    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;

    static {
        /**
         * 模拟多个表 商品信息表 库存表 秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();

        products.put("123456", 100000);
        products.put("123456", 100000);
    }

    public String queryMap(String productId) {
        return "国庆活动,皮蛋瘦肉粥特价,限量："
                + products.get(productId) + "份。"
                + "还剩下：" + stock.get(productId) + "份,"
                + "该商品成功下单用户数目：" + orders.size()
                + "人。。。。";
    }


    @Override
    public String querySecondsKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public synchronized void orderProductMockDiffUser(String productId) {
        //1.查询该商品库存,为0 则活动结束
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(100, "活动结束1234");
        } else {
            //2.下单(模拟不同用户openid不同)
            orders.put(KeyUtils.getUniqueKey(), productId);
            //3.减库存
            stockNum = stockNum - 1;
            try {
                //业务处理
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stock.put(productId, stockNum);
        }
    }


    @Override
    public void orderProductMockDiffUser2(String productId) {
        //加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        boolean result = redisLock.lock(productId, String.valueOf(time));
        if (!result) {
            throw new SellException(110, "哎哟喂,人数太多了");
        }

        //1.查询该商品库存,为0 则活动结束
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(100, "活动结束9999");
        } else {
            //2.下单(模拟不同用户openid不同)
            orders.put(KeyUtils.getUniqueKey(), productId);
            //3.减库存
            stockNum = stockNum - 1;
            try {
                //业务处理
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stock.put(productId, stockNum);
        }

        //解锁
        redisLock.unlock(productId, String.valueOf(time));
    }
}
