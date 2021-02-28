package com.legend.sell.controller;

import com.legend.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * redis和Cache的使用
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/28
 */
@Controller
@Slf4j
@RequestMapping("/cache")
public class RedisCacheController {

    /**
     * 获取数据
     * http://localhost:9000/sell/cache/list
     *
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    @Cacheable(cacheNames = "test", key = "2345678")
    public ResultVO list() {
        List<ResultVO> list = new ArrayList<>();

        ResultVO resultVOResult = new ResultVO();
        for (int i = 0; i < 10; i++) {
            ResultVO resultVO = new ResultVO();
            resultVO.setCode(i);
            resultVO.setMsg("我是message" + i);
            resultVO.setData("redis测试");

            list.add(resultVO);
        }
        resultVOResult.setData(list);
        resultVOResult.setMsg("成功测试");
        resultVOResult.setCode(200);
        return resultVOResult;
    }

    /**
     * 保存更新数据(同时更新redis里面的)
     * http://localhost:9000/sell/cache/save/10
     * <p>
     * CachePut注解仅适用于查询返回的数据对象和保存后返回的数据对象是同一种类型的才可以,如果不同则使用save2方法
     *
     * @return
     */
    @GetMapping("/save/{id}")
    @ResponseBody
    @CachePut(cacheNames = "test", key = "2345678")
    public ResultVO save(@PathVariable("id") Integer id) {
        List<ResultVO> list = new ArrayList<>();

        ResultVO resultVOResult = new ResultVO();
        for (int i = 0; i < 10; i++) {
            ResultVO resultVO = new ResultVO();
            resultVO.setCode(i);
            if (id == 10) {
                resultVO.setMsg("修改后的数据：我是message" + i);
            } else {
                resultVO.setMsg("我是message" + i);
            }
            resultVO.setData("redis测试");

            list.add(resultVO);
        }
        resultVOResult.setData(list);
        resultVOResult.setMsg("成功测试");
        resultVOResult.setCode(200);
        return resultVOResult;
    }

    /**
     * 保存更新数据(同时更新redis里面的)
     * CacheEvict注解是删除缓存
     * http://localhost:9000/sell/cache/save2/10
     *
     * @return
     */
    @GetMapping("/save2/{id}")
    @ResponseBody
    @CacheEvict(cacheNames = "test", key = "2345678")
    public ModelAndView save2(@PathVariable("id") Integer id) {
        List<ResultVO> list = new ArrayList<>();

        ResultVO resultVOResult = new ResultVO();
        for (int i = 0; i < 10; i++) {
            ResultVO resultVO = new ResultVO();
            resultVO.setCode(i);
            if (id == 10) {
                resultVO.setMsg("修改后的数据：我是message2" + i);
            } else {
                resultVO.setMsg("我是message2" + i);
            }
            resultVO.setData("redis测试2");

            list.add(resultVO);
        }
        resultVOResult.setData(list);
        resultVOResult.setMsg("成功测试2");
        resultVOResult.setCode(200);
        return null;
    }


    /**
     * 数据查询(key通过接口的参数设置 key = "#id"叫做spel表达式)
     * CacheEvict注解是删除缓存
     * http://localhost:9000/sell/cache/list2/10
     *
     * @return
     */
    @GetMapping("/list2/{id}")
    @ResponseBody
    @Cacheable(cacheNames = "test", key = "#id", condition = "#id.length() > 1", unless = "#result.code != 200")
    public ResultVO list2(@PathVariable("id") String id) {
        List<ResultVO> list = new ArrayList<>();

        ResultVO resultVOResult = new ResultVO();
        for (int i = 0; i < 10; i++) {
            ResultVO resultVO = new ResultVO();
            resultVO.setCode(i);
            if (Integer.parseInt(id) == 10) {
                resultVO.setMsg("修改后的数据：我是message3" + i);
            } else {
                resultVO.setMsg("我是message3" + i);
            }
            resultVO.setData("redis测试3");

            list.add(resultVO);
        }
        resultVOResult.setData(list);
        resultVOResult.setMsg("成功测试3");
        resultVOResult.setCode(200);
        return resultVOResult;
    }
}
