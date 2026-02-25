package com.selection.effectivepriceservice.adapters.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EffectivePriceResponse(
    Long brandId,
    Long productId,
    Integer priceList,
    LocalDateTime startDate,
    LocalDateTime endDate,
    BigDecimal price,
    String currency) {}
