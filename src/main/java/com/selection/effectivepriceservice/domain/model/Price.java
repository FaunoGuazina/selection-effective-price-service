package com.selection.effectivepriceservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
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

  public Price {
    Objects.requireNonNull(brandId);
    Objects.requireNonNull(productId);
    Objects.requireNonNull(priceList);
    Objects.requireNonNull(priority);
    Objects.requireNonNull(startDate);
    Objects.requireNonNull(endDate);
    Objects.requireNonNull(price);
    Objects.requireNonNull(currency);
  }

  public boolean isEffectiveAt(LocalDateTime applicationDate) {
    Objects.requireNonNull(applicationDate);
    return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
  }
}
