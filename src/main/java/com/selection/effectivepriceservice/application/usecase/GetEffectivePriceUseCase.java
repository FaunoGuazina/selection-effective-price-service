package com.selection.effectivepriceservice.application.usecase;

import com.selection.effectivepriceservice.application.port.PriceRepositoryPort;
import com.selection.effectivepriceservice.domain.model.Price;
import com.selection.effectivepriceservice.domain.service.EffectivePriceSelector;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetEffectivePriceUseCase {

  private final PriceRepositoryPort priceRepositoryPort;

  public Price execute(Long brandId, Long productId, LocalDateTime applicationDate) {

    Objects.requireNonNull(brandId);
    Objects.requireNonNull(productId);
    Objects.requireNonNull(applicationDate);

    List<Price> prices = priceRepositoryPort.findByBrandIdAndProductId(brandId, productId);

    return EffectivePriceSelector.selectEffectivePrice(prices, applicationDate)
        .orElseThrow(
            () ->
                new IllegalStateException(
                    "No effective price found for brandId="
                        + brandId
                        + ", productId="
                        + productId
                        + ", applicationDate="
                        + applicationDate));
  }
}
