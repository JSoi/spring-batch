package com.soi.springbatch.processor;

import com.soi.springbatch.domain.dto.RateDto;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DailyReviewProcessor implements ItemProcessor<RateDto, ReviewBatchDto.ReviewCount> {
    @Override
    public ReviewBatchDto.ReviewCount process(@Nonnull RateDto rateDto) {
        return ReviewBatchDto.ReviewCount.of(rateDto);
    }
}
