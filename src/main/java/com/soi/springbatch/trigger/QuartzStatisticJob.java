package com.soi.springbatch.trigger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static com.soi.springbatch.config.ReviewStatJobConfig.triggerDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuartzStatisticJob extends QuartzJobBean {
    private final JobLauncher jobLauncher;
    private final Job reviewStatJob;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString(triggerDate, ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toString())  // JobParameter로 날짜 전달
                .addLong("runId", System.currentTimeMillis())  // runId는 매번 다르게 설정해야 재실행 가능
                .toJobParameters();
        try {
            jobLauncher.run(reviewStatJob, jobParameters);
        } catch (Exception e){
            log.error("Exception Occurred During Job ReviewStatistics with Parameter {}", jobParameters);
        }
    }
}
