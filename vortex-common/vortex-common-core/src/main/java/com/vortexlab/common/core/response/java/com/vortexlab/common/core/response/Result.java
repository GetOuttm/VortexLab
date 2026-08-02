package com.vortexlab.common.core.response.java.com.vortexlab.common.core.response;

import lombok.Data;

/**
 * 统一 API 响应封装。
 *
 * @param <T> 响应数据的类型
 */
@Data
public class Result<T> {

    /**
     * 业务状态码，200 表示成功，500 表示失败
     */
    private Integer code;

    /**
     * 响应描述信息
     */
    private String message;

    /**
     * 响应数据体
     */
    private T data;

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> failed(ResultCode resultCode) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> failed(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}
