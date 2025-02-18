package com.soi.springbatch.service;

import com.soi.springbatch.domain.entity.Rate;
import com.soi.springbatch.domain.entity.RateStatistics;
import com.soi.springbatch.domain.repository.RateStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RateStatisticsService {
    private final RateStatisticsRepository rateStatisticsRepository;

    public void addRateStatistics(double average, ZonedDateTime from, ZonedDateTime to) {
        rateStatisticsRepository.save(RateStatistics.builder().average(average).startTime(from).endTime(to).build());
    }

    public void deleteStatisticsOfDate(ZonedDateTime now){
        ZonedDateTime start = now.truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime end = start.plusDays(1);
        rateStatisticsRepository.deleteAll(rateStatisticsRepository.getRateStatisticsByStartTimeBetween(start, end));
    }

}
