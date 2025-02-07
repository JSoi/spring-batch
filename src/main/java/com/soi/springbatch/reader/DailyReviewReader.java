package com.soi.springbatch.reader;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soi.springbatch.config.QuerydslPagingItemReader;
import com.soi.springbatch.domain.dto.QRateDto;
import com.soi.springbatch.domain.dto.RateDto;
import com.soi.springbatch.domain.entity.QRate;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyReviewReader {
    private final JPAQueryFactory jpaQueryFactory;
    private ZonedDateTime targetDate;
    private final EntityManagerFactory entityManagerFactory;
    public QuerydslPagingItemReader<RateDto> getReader() {
        if (targetDate == null) {
            targetDate = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
        }
        ZonedDateTime endDate = targetDate.plusDays(1);
        return new QuerydslPagingItemReader<>(entityManagerFactory, 10, chunkSize -> {
            QRate rate = QRate.rate;
            return jpaQueryFactory
                    .select(new QRateDto(rate.id, rate.product.id, rate.value, rate.createdAt))
                    .from(rate)
                    .where(rate.createdAt.goe(targetDate)
                            .and(rate.createdAt.lt(endDate)))
                    .orderBy(rate.product.id.asc());
        });
    }

    public void setStatisticDate(ZonedDateTime targetDate) {
        this.targetDate = targetDate;
    }
}
