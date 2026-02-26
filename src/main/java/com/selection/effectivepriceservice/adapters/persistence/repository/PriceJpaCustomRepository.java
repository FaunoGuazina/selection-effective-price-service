package com.selection.effectivepriceservice.adapters.persistence.repository;

import com.selection.effectivepriceservice.adapters.persistence.entity.PriceJpaEntity;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceJpaCustomRepository {

  Optional<PriceJpaEntity> findEffectivePrice(
      Long brandId, Long productId, LocalDateTime applicationDate);
}
