package com.mmg.commons.config.exception;

public enum ApiErrorCode {

    SUCCESS(200, "操作成功"),
    FAILED(-1, "操作失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    VALIDATE_FAILED(402, "参数检验失败"),
    FORBIDDEN(403, "无权访问"),

    BAD_REQUEST(411, "参数错误"),
    SYSTEM_ERROR(500, "服务器错误"),

    ACCOUNT_NOT_EXIST(301, "账号不存在"),
    ACCOUNT_EXPIRED(302, "账号已过期"),
    LOGIN_PASSWORD_ERROR(303, "密码错误"),
    ACCOUNT_LOCKED(304, "账号被锁定"),
    ACCOUNT_CREDENTIAL_EXPIRED(305, "账号凭证过期"),
    ACCOUNT_DISABLE(306, "账号被禁用"),
    ;

    private final Integer code;
    private final String message;

    ApiErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ApiErrorCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
