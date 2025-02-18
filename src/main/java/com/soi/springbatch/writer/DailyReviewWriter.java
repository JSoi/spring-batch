package com.soi.springbatch.writer;

import com.soi.springbatch.domain.dto.RateStatisticsDto;
import com.soi.springbatch.domain.entity.RateStatistics;
import com.soi.springbatch.processor.ReviewBatchDto;
import com.soi.springbatch.service.RateBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DailyReviewWriter implements ItemWriter<ReviewBatchDto.ReviewCount> {
    private final RateBatchService rateBatchService;

    @Override
    public void write(Chunk<? extends ReviewBatchDto.ReviewCount> chunk) {
        Map<Long, List<ReviewBatchDto.ReviewCount>> groupedByProduct = chunk.getItems().stream()
                .collect(Collectors.groupingBy(ReviewBatchDto.ReviewCount::getProductId));
        List<RateStatisticsDto> saveList = groupedByProduct.entrySet().stream().map(entry -> {
            Long productId = entry.getKey();
            List<ReviewBatchDto.ReviewCount> statisticsList = entry.getValue().stream()
                    .sorted(Comparator.comparing(ReviewBatchDto.ReviewCount::getCreatedAt)).toList();
            long count = statisticsList.stream().mapToLong(ReviewBatchDto.ReviewCount::getCount).sum();
            double average = statisticsList.stream().mapToDouble(ReviewBatchDto.ReviewCount::getRate).average().orElse(0);

            return RateStatisticsDto.builder()
                    .product(productId)
                    .average(average)
                    .count(count)
                    .startTime(statisticsList.get(0).getCreatedAt())
                    .endTime(statisticsList.get(statisticsList.size() - 1).getCreatedAt())
                    .build();
        }).toList();
        for (RateStatisticsDto rateStatisticsDto : saveList) {
             rateBatchService.gateRateStatisticsOfProduct(rateStatisticsDto.getStartTime(), rateStatisticsDto.getProduct())
                     .ifPresentOrElse(a->a.merge(rateStatisticsDto), ()-> rateBatchService.saveDailyStatistics(rateStatisticsDto));
        }
//        rateBatchService.gateRateStatisticsOfProduct()
    }
}
