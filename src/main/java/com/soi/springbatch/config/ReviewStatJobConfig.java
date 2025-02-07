package com.soi.springbatch.config;

import com.soi.springbatch.domain.dto.RateDto;
import com.soi.springbatch.listener.DailyReviewListener;
import com.soi.springbatch.processor.DailyReviewProcessor;
import com.soi.springbatch.processor.ReviewBatchDto;
import com.soi.springbatch.reader.DailyReviewReader;
import com.soi.springbatch.writer.DailyReviewWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ReviewStatJobConfig {
    public static final String triggerDate = "REVIEW_TARGET_DATE";

    private final DailyReviewReader dailyReviewReader;
    private final DailyReviewProcessor dailyReviewProcessor;
    private final DailyReviewListener dailyReviewListener;
    private final DailyReviewWriter dailyReviewWriter;

    @Bean
    public Job reviewStatisticsJob(JobRepository jobRepository, Step reviewStatStep) {
        return new JobBuilder("reviewStatJob", jobRepository)
                .start(reviewStatStep)
                .build();
    }

    @Bean
    public Step reviewStatisticsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("reviewStatisticsStep", jobRepository)
                .<RateDto, ReviewBatchDto.ReviewCount>chunk(10, transactionManager)
                .reader(dailyReviewReader.getReader())
                .processor(dailyReviewProcessor)
                .writer(dailyReviewWriter)
                .listener(dailyReviewListener)
                .build();
    }
}
