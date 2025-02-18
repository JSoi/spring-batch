package com.soi.springbatch.trigger;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(QuartzStatisticJob.class)
                .withIdentity("dailyStatisticsJob")
                .storeDurably()
                .build();
    }

    @Bean
    CronTrigger dailyStatisticsTrigger(){
        return TriggerBuilder.newTrigger()
                .withIdentity("daily", "statistics")
                .forJob("dailyStatisticsJob")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 ? * *"))
                .build();
    }
}
