package com.soi.springbatch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class DefaultRetryListener implements RetryListener {
    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        log.info("Retry started: method={}, retryCount={}",
                callback.getClass().getSimpleName(), context.getRetryCount());
        return true;
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.warn("Retry failed: method={}, attempt={}/{}, exception={}",
                callback.getClass().getSimpleName(),
                context.getRetryCount(),
                context.getAttribute(RetryContext.MAX_ATTEMPTS),
                throwable.getMessage());
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        if (throwable == null) {
            log.info("Retry SUCCESS: method={}, totalAttempts={}", callback.getClass().getSimpleName(), context.getRetryCount());
        } else {
            log.error("Retry EXHAUSTED: method={}, totalAttempts={}, lastException={}",
                    callback.getClass().getSimpleName(),
                    context.getRetryCount(),
                    throwable.getMessage());
        }
    }
}
