package com.mmg.commons.config.exception;


/**
 * 自定义API异常
 *
 * @author fan
 * @since 2020/2/27
 */
public class ApiException extends RuntimeException {
    private ApiErrorCode errorCode;
    private String message;

    public ApiException(ApiErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(ApiErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
