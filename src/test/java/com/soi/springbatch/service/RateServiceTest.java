package com.soi.springbatch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
class RateServiceTest {
    @Autowired
    private RateService rateService;

    @Test
    @Commit
    void insertRate() {
        rateService.insertRate(5, 2L);
        rateService.insertRate(5, 2L);
        rateService.insertRate(5, 2L);
        rateService.insertRate(5, 2L);
        rateService.insertRate(5, 2L);
        rateService.insertRate(5, 2L);
    }
}