package com.selection.effectivepriceservice.domain.service;

import com.selection.effectivepriceservice.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EffectivePriceSelector {

  private static final Comparator<Price> BY_PRIORITY_THEN_PRICE_LIST =
      Comparator.comparing(Price::priority).thenComparing(Price::priceList);

  Optional<Price> selectEffectivePrice(List<Price> prices, LocalDateTime applicationDate) {

    Objects.requireNonNull(prices);
    Objects.requireNonNull(applicationDate);

    return prices.stream()
        .filter(price -> price.isEffectiveAt(applicationDate))
        .max(BY_PRIORITY_THEN_PRICE_LIST);
  }
}
