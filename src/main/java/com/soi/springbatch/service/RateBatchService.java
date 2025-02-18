package com.soi.springbatch.service;

import com.soi.springbatch.domain.dto.RateStatisticsDto;
import com.soi.springbatch.domain.entity.RateStatistics;
import com.soi.springbatch.domain.repository.RateQueryRepository;
import com.soi.springbatch.domain.repository.RateStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    public List<RateStatisticsDto> getRateStatistics(ZonedDateTime targetDate) {
        return rateQueryRepository.gateRateAverage(targetDate.truncatedTo(ChronoUnit.DAYS), targetDate.truncatedTo(ChronoUnit.DAYS).plusDays(1));
    }

    @Transactional
    public Optional<RateStatistics> gateRateStatisticsOfProduct(ZonedDateTime targetDate, Long productId) {
        return rateStatisticsRepository.findRateStatisticsByProductAndStartTimeGreaterThanEqual(productId, targetDate.truncatedTo(ChronoUnit.DAYS));
    }

    @Transactional
    public <T extends  RateStatisticsDto> void saveDailyStatistics(T ... rateStatisticsDto) {
        rateStatisticsRepository.saveAll(Arrays.stream(rateStatisticsDto).map(RateStatistics::of).toList());
    }
}
