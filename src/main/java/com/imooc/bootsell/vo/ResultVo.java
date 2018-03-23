package com.imooc.bootsell.vo;

import lombok.Data;

/**
 * http请求返回的最外层对象
 *
 * @param <T>
 */
@Data
public class ResultVo<T> {

    /**
     * 请求码
     */
    private Integer code;

    /**
     * 请求返回消息消息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;

}