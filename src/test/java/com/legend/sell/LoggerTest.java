package com.legend.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {

    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(LoggerTest.class);


    @Test
    public void test1() {
        String name = "legend";
        log.info("正在做LOGGER测试.....{}",name);
    }
}
