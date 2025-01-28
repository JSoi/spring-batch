package com.soi.springbatch.service;

import com.soi.springbatch.domain.entity.RateStatistics;
import com.soi.springbatch.domain.repository.RateQueryRepository;
import com.soi.springbatch.domain.repository.RateStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RateBatchService {
    private final RateQueryRepository rateQueryRepository;
    private final RateStatisticsRepository rateStatisticsRepository;

    public void processAverageOfDay(ZonedDateTime targetDate) {
        List<RateStatistics> newStatistics =
                rateQueryRepository.gateRateAverage(targetDate.truncatedTo(ChronoUnit.DAYS), targetDate.truncatedTo(ChronoUnit.DAYS).plusDays(1))
                        .stream().map(RateStatistics::of).toList();
        rateStatisticsRepository.saveAll(newStatistics);
    }
}
