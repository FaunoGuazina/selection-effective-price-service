package com.selection.effectivepriceservice.application.usecase;

import com.selection.effectivepriceservice.application.port.PriceRepositoryPort;
import com.selection.effectivepriceservice.domain.exception.EffectivePriceNotFoundException;
import com.selection.effectivepriceservice.domain.model.Price;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEffectivePriceUseCase {

  private final PriceRepositoryPort priceRepositoryPort;

  public Price execute(Long brandId, Long productId, LocalDateTime applicationDate) {

    return priceRepositoryPort
        .findEffectivePrice(brandId, productId, applicationDate)
        .orElseThrow(
            () ->
                new EffectivePriceNotFoundException(
                    "No effective price found for brandId=%s, productId=%s, applicationDate=%s"
                        .formatted(brandId, productId, applicationDate)));
  }
}
