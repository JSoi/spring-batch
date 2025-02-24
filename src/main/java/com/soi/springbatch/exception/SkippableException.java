package com.soi.springbatch.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SkippableException extends RuntimeException {
    public SkippableException(String message) {
        super(message);
        log.warn("SkippableException Occured : {}", message);
    }
}
