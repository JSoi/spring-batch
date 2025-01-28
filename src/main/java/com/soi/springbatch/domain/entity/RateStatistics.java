package com.soi.springbatch.domain.entity;

import com.soi.springbatch.domain.dto.RateStatisticsDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.ZonedDateTime;

@Entity
@Builder
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rate_statistics")
public class RateStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long product;

    @Column
    private Double average;

    @Column
    private Long count;

    @Column
    private ZonedDateTime startTime;

    @Column
    private ZonedDateTime endTime;

    @Column
    ZonedDateTime createdAt;

    @Column
    ZonedDateTime updatedAt;

    @PrePersist
    void persist() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    void update() {
        this.updatedAt = ZonedDateTime.now();
    }

    public static RateStatistics of(RateStatisticsDto rateStatisticsDto) {
        return RateStatistics.builder()
                .product(rateStatisticsDto.getProduct())
                .average(rateStatisticsDto.getAverage())
                .count(rateStatisticsDto.getCount())
                .startTime(rateStatisticsDto.getStartTime())
                .endTime(rateStatisticsDto.getEndTime())
                .build();
    }
}
