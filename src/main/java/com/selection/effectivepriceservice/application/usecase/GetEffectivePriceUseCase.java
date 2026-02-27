package com.selection.effectivepriceservice.application.usecase;

import com.selection.effectivepriceservice.application.port.PriceRepositoryPort;
import com.selection.effectivepriceservice.domain.exception.EffectivePriceNotFoundException;
import com.selection.effectivepriceservice.domain.model.Price;
import com.selection.effectivepriceservice.domain.service.EffectivePriceSelector;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetEffectivePriceUseCase {

  private final PriceRepositoryPort priceRepositoryPort;

  @Transactional(readOnly = true)
  public Price execute(Long brandId, Long productId, LocalDateTime applicationDate) {

    List<Price> prices =
        priceRepositoryPort.findPricesByBrandProductAndDate(brandId, productId, applicationDate);

    return resolveEffectivePrice(prices, brandId, productId, applicationDate);
  }

  private Price resolveEffectivePrice(
      List<Price> prices, Long brandId, Long productId, LocalDateTime applicationDate) {

    return EffectivePriceSelector.selectEffectivePrice(prices, applicationDate)
        .orElseThrow(
            () ->
                new EffectivePriceNotFoundException(
                    """
                    No effective price found for brandId=%s, productId=%s, applicationDate=%s
                    """
                        .formatted(brandId, productId, applicationDate)));
  }
}
