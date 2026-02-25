package com.selection.effectivepriceservice.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.selection.effectivepriceservice.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EffectivePriceSelectorTest {

  @Test
  @DisplayName("Should return highest priority price when multiple prices overlap")
  void shouldReturnHighestPriorityWhenMultipleMatch() {

    var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

    var lowPriority =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(1)
            .priority(0)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
            .price(BigDecimal.valueOf(35.50))
            .currency("EUR")
            .build();

    var highPriority =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(2)
            .priority(1)
            .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
            .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
            .price(BigDecimal.valueOf(25.45))
            .currency("EUR")
            .build();

    var result =
        EffectivePriceSelector.selectEffectivePrice(
            List.of(lowPriority, highPriority), applicationDate);

    assertThat(result).isPresent();
    assertThat(result.get().priceList()).isEqualTo(2);
  }

  @Test
  @DisplayName("Should return empty when no price matches date")
  void shouldReturnEmptyWhenNoMatch() {

    var applicationDate = LocalDateTime.of(2019, 1, 1, 10, 0);

    var price =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(1)
            .priority(0)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
            .price(BigDecimal.valueOf(35.50))
            .currency("EUR")
            .build();

    var result = EffectivePriceSelector.selectEffectivePrice(List.of(price), applicationDate);

    assertThat(result).isEmpty();
  }
}
