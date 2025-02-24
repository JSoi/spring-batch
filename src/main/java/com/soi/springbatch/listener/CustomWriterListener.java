package com.soi.springbatch.listener;

import com.soi.springbatch.enums.JobName;
import com.soi.springbatch.reader.DailyReviewReader;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.batch.core.*;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

import static com.soi.springbatch.config.ReviewStatJobConfig.triggerDate;

@Aspect
@Slf4j
@RequiredArgsConstructor
public abstract class CustomWriterListener<T> implements ItemWriteListener<T> {
    protected JobName jobName;

    protected CustomWriterListener(JobName jobName) {
        this.jobName = jobName;
    }

    @Override
    public void beforeWrite(@Nonnull Chunk items) {
        log.info("beforeWrite {}", this.jobName);
        ItemWriteListener.super.beforeWrite(items);
    }

    @Override
    public void afterWrite(@Nonnull Chunk items) {
        log.info("afterWrite {}", this.jobName);
        ItemWriteListener.super.afterWrite(items);
    }

    @Override
    public void onWriteError(Exception exception, Chunk items) {
        log.error("on write error {}", this.jobName);
        ItemWriteListener.super.onWriteError(exception, items);
    }

}
