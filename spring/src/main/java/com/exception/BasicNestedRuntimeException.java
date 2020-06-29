package com.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

import java.text.MessageFormat;

/**
 * 基本异常
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:36
 */
@Slf4j
public class BasicNestedRuntimeException extends RuntimeException {

    public BasicNestedRuntimeException() {
        super();
        log.error(this.getMessage());
    }

    public BasicNestedRuntimeException(String format, Object... args) {
        super(MessageFormatter.arrayFormat(format, args).getMessage());
        log.error(this.getMessage());
    }

    public BasicNestedRuntimeException(String message) {
        super(message);
        log.error(this.getMessage());
    }

    public BasicNestedRuntimeException(String message, Throwable cause) {
        super(message, cause);
        log.error(this.getMessage());
    }

    public BasicNestedRuntimeException(Throwable cause) {
        super(cause);
        log.error(this.getMessage());
    }

    public BasicNestedRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        log.error(this.getMessage());
    }
}
