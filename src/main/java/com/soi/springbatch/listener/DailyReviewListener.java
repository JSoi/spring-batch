package com.soi.springbatch.listener;

import com.soi.springbatch.reader.DailyReviewReader;
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
public class DailyReviewListener implements StepExecutionListener {
    private final DailyReviewReader dailyReviewReader;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        JobParameters jobParameters = stepExecution.getJobParameters();
        String triggerTime = jobParameters.getString(triggerDate);

        if (triggerTime == null) {
            throw new IllegalArgumentException("JobParameter 'triggerDate' is required.");
        }

        ZonedDateTime startOfDay = ZonedDateTime.parse(triggerTime);
        dailyReviewReader.setStatisticDate(startOfDay);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
