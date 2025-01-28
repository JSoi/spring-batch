package com.soi.springbatch.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.ZonedDateTime;

@Entity
@Builder
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    @Size(min = 1, max = 5)
    private Integer value = 0;

    ZonedDateTime createdAt;

    @PrePersist
    void persist(){
        this.createdAt = ZonedDateTime.now();
    }
}
