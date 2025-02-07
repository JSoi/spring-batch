package com.soi.springbatch.service;

import com.soi.springbatch.domain.entity.Product;
import com.soi.springbatch.domain.entity.Rate;
import com.soi.springbatch.domain.repository.ProductRepository;
import com.soi.springbatch.domain.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateService {
    private final RateRepository rateRepository;
    private final ProductRepository productRepository;
    public void insertRate(int rate, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        rateRepository.save(Rate.builder().value(rate).product(product).build());
    }
}
