package com.soi.springbatch.processor;

import com.soi.springbatch.domain.dto.RateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

public class ReviewBatchDto {
    // statistic of batch counting
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ReviewCount {
        private Long count;
        private Long productId;
        private int rate;
        private ZonedDateTime createdAt;

        public static ReviewCount of(RateDto rateDto) {
            return ReviewCount.builder()
                    .count(1L)
                    .productId(rateDto.getProductId())
                    .rate(rateDto.getValue())
                    .createdAt(rateDto.getCreatedAt())
                    .build();
        }
    }
}
