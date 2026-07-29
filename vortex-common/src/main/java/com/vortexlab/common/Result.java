package com.vortexlab.common;

import lombok.Data;

/**
 * 统一 API 响应封装。
 *
 * @param <T> 响应数据的类型
 */
@Data
public class Result<T> {

    /** 业务状态码，200 表示成功，500 表示失败 */
    private Integer code;

    /** 响应描述信息 */
    private String msg;

    /** 响应数据体 */
    private T data;

    /**
     * 构建成功响应。
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return code=200 的成功结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    /**
     * 构建失败响应。
     *
     * @param msg 错误描述
     * @param <T> 数据类型
     * @return code=500 的失败结果
     */
    public static <T> Result<T> fail(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
}
