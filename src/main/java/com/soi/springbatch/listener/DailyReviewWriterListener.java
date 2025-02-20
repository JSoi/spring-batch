package com.soi.springbatch.listener;

import com.soi.springbatch.enums.JobName;
import com.soi.springbatch.processor.ReviewBatchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DailyReviewWriterListener extends CustomWriterListener<ReviewBatchDto.ReviewCount> {
    public DailyReviewWriterListener() {
        super(JobName.DAILY_STATISTICS);
    }
}
