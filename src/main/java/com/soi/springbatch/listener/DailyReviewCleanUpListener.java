package com.soi.springbatch.listener;

import com.soi.springbatch.service.RateStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

import static com.soi.springbatch.config.ReviewStatJobConfig.triggerDate;

@Component
@RequiredArgsConstructor
public class DailyReviewCleanUpListener implements StepExecutionListener {
    private final RateStatisticsService rateStatisticsService;
    @Override
    public void beforeStep(StepExecution stepExecution) {
        JobParameters jobParameters = stepExecution.getJobParameters();
        String triggerTime = jobParameters.getString(triggerDate);
        ZonedDateTime startOfDay = ZonedDateTime.parse(triggerTime);
        rateStatisticsService.deleteStatisticsOfDate(startOfDay);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
