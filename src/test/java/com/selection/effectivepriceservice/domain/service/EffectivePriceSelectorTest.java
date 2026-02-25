package com.selection.effectivepriceservice.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.selection.effectivepriceservice.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EffectivePriceSelectorTest {

  private final EffectivePriceSelector selector = new EffectivePriceSelector();

  @Test
  @DisplayName("Should return highest priority price when multiple prices overlap")
  void shouldReturnHighestPriorityWhenMultipleMatch() {

    var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

    var lowPriority =
        new Price(
            1L,
            35455L,
            1,
            0,
            LocalDateTime.of(2020, 6, 14, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59),
            BigDecimal.valueOf(35.50),
            "EUR");

    var highPriority =
        new Price(
            1L,
            35455L,
            2,
            1,
            LocalDateTime.of(2020, 6, 14, 15, 0),
            LocalDateTime.of(2020, 6, 14, 18, 30),
            BigDecimal.valueOf(25.45),
            "EUR");

    var result = selector.selectEffectivePrice(List.of(lowPriority, highPriority), applicationDate);

    assertThat(result).isPresent();
    assertThat(result.get().priceList()).isEqualTo(2);
  }

  @Test
  @DisplayName("Should return empty when no price matches date")
  void shouldReturnEmptyWhenNoMatch() {

    var applicationDate = LocalDateTime.of(2019, 1, 1, 10, 0);

    var price =
        new Price(
            1L,
            35455L,
            1,
            0,
            LocalDateTime.of(2020, 6, 14, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59),
            BigDecimal.valueOf(35.50),
            "EUR");

    var result = selector.selectEffectivePrice(List.of(price), applicationDate);

    assertThat(result).isEmpty();
  }
}
