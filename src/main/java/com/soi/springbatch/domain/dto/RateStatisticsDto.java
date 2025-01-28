package com.soi.springbatch.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.soi.springbatch.domain.entity.RateStatistics;
import lombok.Value;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * DTO for {@link RateStatistics}
 */
@Value
public class RateStatisticsDto implements Serializable {
    Long product;
    Double average;
    Long count;
    ZonedDateTime startTime;
    ZonedDateTime endTime;

    @QueryProjection
    public RateStatisticsDto(Long product, Double average, Long count, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.product = product;
        this.average = average;
        this.count = count;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}