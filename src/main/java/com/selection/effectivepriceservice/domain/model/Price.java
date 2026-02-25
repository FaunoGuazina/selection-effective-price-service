package com.selection.effectivepriceservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Price(
    Long brandId,
    Long productId,
    Integer priceList,
    Integer priority,
    LocalDateTime startDate,
    LocalDateTime endDate,
    BigDecimal price,
    String currency) {

  public boolean isEffectiveAt(LocalDateTime applicationDate) {
    return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
  }
}
