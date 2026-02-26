package com.selection.effectivepriceservice.adapters.persistence.repository;

import com.selection.effectivepriceservice.adapters.persistence.entity.PriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository
    extends JpaRepository<PriceJpaEntity, Long>, PriceJpaCustomRepository {}
