package com.soi.springbatch.task;


import com.soi.springbatch.service.RateBatchService;
import com.soi.springbatch.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyStatisticsTask implements Tasklet, StepExecutionListener {
    private final RateBatchService rateBatchService;
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("DailyStatisticsTask beforeStep");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("DailyStatisticsTask execute");
        rateBatchService.processAverageOfDay(ZonedDateTime.now());
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("DailyStatisticsTask afterStep");
        return ExitStatus.COMPLETED;
    }
}
