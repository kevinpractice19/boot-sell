package com.imooc.bootsell.utils;

import com.imooc.bootsell.vo.ResultVo;

/**
 * http请求最外层对象赋值
 */
public class ResultVoUtil {

    public static ResultVo success(Object object) {
        ResultVo resultVO = new ResultVo();
        resultVO.setData(object);
        resultVO.setCode(100);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVo success() {
        return success(null);
    }

    public static ResultVo error(Integer code, String msg) {
        ResultVo resultVO = new ResultVo<>();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
