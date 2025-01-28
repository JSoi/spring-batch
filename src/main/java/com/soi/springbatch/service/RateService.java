package com.soi.springbatch.service;

import com.soi.springbatch.domain.entity.Product;
import com.soi.springbatch.domain.entity.Rate;
import com.soi.springbatch.domain.entity.RateStatistics;
import com.soi.springbatch.domain.repository.ProductRepository;
import com.soi.springbatch.domain.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateService {
    private final RateRepository rateRepository;
    private final ProductRepository productRepository;
    private final RateStatisticsService rateStatisticsService;
    public void insertRate(int rate, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        rateRepository.save(Rate.builder().value(rate).product(product).build());
    }

    public RateStatistics getRateOfTime(ZonedDateTime start) {
        return null;
    }
}
