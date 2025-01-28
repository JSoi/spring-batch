package com.soi.springbatch.domain.repository;

import com.soi.springbatch.domain.entity.RateStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateStatisticsRepository extends JpaRepository<RateStatistics, Long> {
}