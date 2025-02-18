package com.soi.springbatch.domain.repository;

import com.soi.springbatch.domain.dto.RateStatisticsDto;
import com.soi.springbatch.domain.entity.RateStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RateStatisticsRepository extends JpaRepository<RateStatistics, Long> {
    Optional<RateStatistics> findRateStatisticsByProductAndStartTimeGreaterThanEqual(Long productId, ZonedDateTime zonedDateTime);
    List<RateStatistics> getRateStatisticsByStartTimeBetween(ZonedDateTime start, ZonedDateTime end);
}