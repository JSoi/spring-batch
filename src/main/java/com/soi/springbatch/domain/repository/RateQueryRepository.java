package com.soi.springbatch.domain.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soi.springbatch.domain.dto.QRateStatisticsDto;
import com.soi.springbatch.domain.dto.RateStatisticsDto;
import com.soi.springbatch.domain.entity.QProduct;
import com.soi.springbatch.domain.entity.QRate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.beans.Expression;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RateQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    QProduct product = QProduct.product;
    QRate rate = QRate.rate;
    public List<RateStatisticsDto> gateRateAverage(ZonedDateTime start, ZonedDateTime end) {
        return jpaQueryFactory.select(new QRateStatisticsDto(product.id, rate.value.avg(), rate.count(), Expressions.asTime(start), Expressions.asTime(end)))
                .from(rate)
                .where(rate.createdAt.between(start, end))
                .groupBy(product.id)
                .fetch();
    }
}
