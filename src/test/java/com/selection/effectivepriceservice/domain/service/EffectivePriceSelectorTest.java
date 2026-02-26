package com.selection.effectivepriceservice.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.selection.effectivepriceservice.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class EffectivePriceSelectorTest {

  @Test
  @DisplayName("Should return highest priority price when multiple prices overlap")
  void shouldReturnHighestPriorityWhenMultipleMatch() {

    var applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

    var lowPriority = price(1, 0, 0, 1, 23, 59, 35.50);
    var highPriority = price(2, 1, 15, 0, 18, 30, 25.45);

    var result =
        EffectivePriceSelector.selectEffectivePrice(
            List.of(lowPriority, highPriority), applicationDate);

    assertThat(result).contains(highPriority);
  }

  @ParameterizedTest
  @MethodSource("boundaryDates")
  @DisplayName("Should include price when application date equals boundary")
  void shouldIncludeWhenEqualsBoundary(LocalDateTime boundary) {

    var price = price(1, 0, 0, 0, 23, 59, 35.50);

    var result = EffectivePriceSelector.selectEffectivePrice(List.of(price), boundary);

    assertThat(result).contains(price);
  }

  static Stream<LocalDateTime> boundaryDates() {
    return Stream.of(LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 6, 14, 23, 59));
  }

  @Test
  @DisplayName("Should return priceList wins with highest priceList when priorities are equal")
  void shouldResolveDeterministicallyWhenPriorityTie() {

    var applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

    var first = price(1, 1, 0, 0, 23, 59, 35.50);
    var second = price(2, 1, 0, 0, 23, 59, 30.00);

    var result =
        EffectivePriceSelector.selectEffectivePrice(List.of(first, second), applicationDate);

    assertThat(result).contains(second);
  }

  @Test
  @DisplayName("Should return empty when multiple prices exist but none are effective")
  void shouldReturnEmptyWhenMultipleButNoneEffective() {

    var applicationDate = LocalDateTime.of(2019, 1, 1, 10, 0);

    var first = price(1, 0, 0, 0, 23, 59, 35.50);
    var second =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .priceList(2)
            .priority(1)
            .startDate(LocalDateTime.of(2021, 1, 1, 0, 0))
            .endDate(LocalDateTime.of(2021, 12, 31, 23, 59))
            .price(BigDecimal.valueOf(40.00))
            .currency("EUR")
            .build();

    var result =
        EffectivePriceSelector.selectEffectivePrice(List.of(first, second), applicationDate);

    assertThat(result).isNotPresent();
  }

  @Test
  @DisplayName("Should return empty when no price matches date")
  void shouldReturnEmptyWhenNoMatch() {

    var applicationDate = LocalDateTime.of(2019, 1, 1, 10, 0);
    var price = price(1, 0, 0, 0, 23, 59, 35.50);

    var result = EffectivePriceSelector.selectEffectivePrice(List.of(price), applicationDate);

    assertThat(result).isNotPresent();
  }

  // ---------- Test factory ----------

  private Price price(
      int priceList,
      int priority,
      int startHour,
      int startMinute,
      int endHour,
      int endMinute,
      double amount) {

    return Price.builder()
        .brandId(1L)
        .productId(35455L)
        .priceList(priceList)
        .priority(priority)
        .startDate(LocalDateTime.of(2020, 6, 14, startHour, startMinute))
        .endDate(LocalDateTime.of(2020, 6, 14, endHour, endMinute))
        .price(BigDecimal.valueOf(amount))
        .currency("EUR")
        .build();
  }
}
