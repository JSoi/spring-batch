package com.soi.springbatch.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryableException extends RuntimeException {
    public RetryableException(String message) {
        super(message);
        log.warn("RetryableException Occured : {}", message);
    }
}
