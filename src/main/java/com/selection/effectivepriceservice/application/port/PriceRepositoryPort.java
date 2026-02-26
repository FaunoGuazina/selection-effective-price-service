package com.selection.effectivepriceservice.application.port;

import com.selection.effectivepriceservice.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {

  Optional<Price> findEffectivePrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
