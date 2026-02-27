package com.selection.effectivepriceservice.application.port;

import com.selection.effectivepriceservice.domain.model.Price;
import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {

  List<Price> findPricesByBrandProductAndDate(
      Long brandId, Long productId, LocalDateTime applicationDate);
}
