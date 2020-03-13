package com.legend.sell.utils;


import com.legend.sell.enums.ResultCodeEnums;
import com.legend.sell.vo.ResultVO;

/**
 * 结果工具类
 *
 * @author legend
 */
public class ResultVOUtils {

    /**
     * ResultVO的封装
     *
     * @param data
     * @return
     */
    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultCodeEnums.OK.getCode());
        resultVO.setData(data);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    /**
     * 错误信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }

    /**
     * 错误信息
     *
     * @param resultCodeEnums
     * @return
     */
    public static ResultVO error(ResultCodeEnums resultCodeEnums) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(resultCodeEnums.getMsg());
        resultVO.setCode(resultCodeEnums.getCode());
        return resultVO;
    }
}
