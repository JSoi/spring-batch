package com.soi.springbatch.domain.repository;

import com.soi.springbatch.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}