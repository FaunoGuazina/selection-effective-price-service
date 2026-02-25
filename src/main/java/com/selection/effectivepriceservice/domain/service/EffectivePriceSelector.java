package com.selection.effectivepriceservice.domain.service;

import com.selection.effectivepriceservice.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class EffectivePriceSelector {

  private static final Comparator<Price> BY_PRIORITY = Comparator.comparing(Price::priority);

  private EffectivePriceSelector() {}

  public static Optional<Price> selectEffectivePrice(
      List<Price> prices, LocalDateTime applicationDate) {

    Objects.requireNonNull(prices);
    Objects.requireNonNull(applicationDate);

    return prices.stream().filter(price -> price.isEffectiveAt(applicationDate)).max(BY_PRIORITY);
  }
}
