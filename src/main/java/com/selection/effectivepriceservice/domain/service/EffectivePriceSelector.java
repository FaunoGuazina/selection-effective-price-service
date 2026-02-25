package com.selection.effectivepriceservice.domain.service;

import com.selection.effectivepriceservice.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EffectivePriceSelector {

  public Optional<Price> selectEffectivePrice(List<Price> prices, LocalDateTime applicationDate) {
    return prices.stream()
        .filter(price -> price.isEffectiveAt(applicationDate))
        .max(Comparator.comparing(Price::priority));
  }
}
