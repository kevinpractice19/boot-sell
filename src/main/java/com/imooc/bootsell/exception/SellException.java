package com.imooc.bootsell.exception;

import com.imooc.bootsell.enums.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = code;
    }


    public SellException(Integer code , String msg){
        super(msg);
        this.code = code;
    }
}
