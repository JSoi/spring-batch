package com.soi.springbatch.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * DTO for {@link com.soi.springbatch.domain.entity.Rate}
 */
@Value
public class RateDto implements Serializable {
    Long id;
    Long productId;
    Integer value;
    ZonedDateTime createdAt;

    @QueryProjection
    public RateDto(Long id, Long productId, Integer value, ZonedDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.value = value;
        this.createdAt = createdAt;
    }
}