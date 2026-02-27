package com.selection.effectivepriceservice.adapters.persistence.repository;

import com.selection.effectivepriceservice.adapters.persistence.entity.PriceJpaEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface PriceJpaCustomRepository {

  List<PriceJpaEntity> findPricesByBrandProductAndDate(
      Long brandId, Long productId, LocalDateTime applicationDate);
}
