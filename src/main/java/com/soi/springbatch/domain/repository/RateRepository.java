package com.soi.springbatch.domain.repository;

import com.soi.springbatch.domain.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    Stream<Rate> findByCreatedAtBetween(ZonedDateTime createdAtStart, ZonedDateTime createdAtEnd);
    Stream<Rate> findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(ZonedDateTime createdAt, ZonedDateTime createdAt1);
    @Query("SELECT r FROM Rate r WHERE r.createdAt >= ?1 AND r.createdAt < ?2")
    Stream<Rate> find(ZonedDateTime start, ZonedDateTime end);


}