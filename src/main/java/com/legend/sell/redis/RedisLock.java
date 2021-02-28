package com.legend.sell.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * redis分布式锁
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/28
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * redis加锁
     *
     * @param key   productId
     * @param value 当前时间 + 超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }

        //============================死锁处理(程序中途出现异常,则会没法进行解锁的步骤操作,会发生死锁)============================
        //锁超时处理 只会是其中一个线程拿到锁
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        //如果锁过期
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {

            //获取上一个锁的时间
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);

            //
            if (!StringUtils.isEmpty(oldValue) && currentValue.equals(oldValue)) {
                return true;
            }
        }
        //============================死锁处理============================

        return false;
    }

    public void unlock(String key, String value) {
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {

                stringRedisTemplate.opsForValue().getOperations().delete(key);

            }
        } catch (Exception e) {
            log.error("【redis分布式锁】解锁异常");
            e.printStackTrace();
        }
    }
}
