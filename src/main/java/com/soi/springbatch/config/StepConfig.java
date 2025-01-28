package com.soi.springbatch.config;

import com.soi.springbatch.task.DailyStatisticsTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepConfig {
    @Bean
    public Job job(JobRepository jobRepository, DailyStatisticsTask dailyStatisticsTask, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job", jobRepository)
                .start(this.step(jobRepository, dailyStatisticsTask, transactionManager))
                .build();
    }
    @Bean
    public TaskletStep step(JobRepository jobRepository, Tasklet myTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("myStep", jobRepository)
                .tasklet(myTasklet, transactionManager)
                .build();
    }
}
