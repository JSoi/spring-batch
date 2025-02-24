package com.soi.springbatch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class DefaultSkipListener<T, S> implements SkipListener<T, S> {
    @Override
    public void onSkipInRead(Throwable t) {
        // 읽는 중에 오류가 발생하여 아이템을 건너뛰었을 때
        log.error("item read error, skip: {}", t.getMessage(), t);
    }

    @Override
    public void onSkipInProcess(T item, Throwable t) {
        // 처리 중에 오류가 발생하여 아이템을 건너뛰었을 때
        log.error("process error: {}, msg: {}", item, t.getMessage(), t);
    }

    @Override
    public void onSkipInWrite(S item, Throwable t) {
        // 쓰기 중에 오류가 발생하여 아이템을 건너뛰었을 때
        log.error("write error, skipped: {}", t.getMessage(), t);
    }
}
