package com.ibs.backed.exception;


/**
 * 校验失败
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
    }
}
