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
    final var errMsg = " must not be null";
    Objects.requireNonNull(brandId, "brandId" + errMsg);
    Objects.requireNonNull(productId, "productId" + errMsg);
    Objects.requireNonNull(priceList, "priceList" + errMsg);
    Objects.requireNonNull(priority, "priority" + errMsg);
    Objects.requireNonNull(startDate, "startDate" + errMsg);
    Objects.requireNonNull(endDate, "endDate" + errMsg);
    Objects.requireNonNull(price, "price" + errMsg);
    Objects.requireNonNull(currency, "currency" + errMsg);

    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("startDate must be before or equal to endDate");
    }
  }
}
