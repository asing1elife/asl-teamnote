package com.asing1elife.teamnote.core.exception;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
