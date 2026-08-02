package com.vortexlab.common.core.response.java.com.vortexlab.common.core.response;

public enum ResultCode {

    SUCCESS(200,"success"),

    SYSTEM_ERROR(500,"系统异常"),

    PARAM_ERROR(400,"参数错误"),

    UNAUTHORIZED(401,"未登录"),

    FORBIDDEN(403,"没有权限"),

    USER_NOT_EXIST(10001,"用户不存在"),

    PASSWORD_ERROR(10002,"密码错误"),

    TOKEN_INVALID(10003,"Token无效"),

    TOKEN_EXPIRED(10004,"Token已过期");

    private final Integer code;

    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
