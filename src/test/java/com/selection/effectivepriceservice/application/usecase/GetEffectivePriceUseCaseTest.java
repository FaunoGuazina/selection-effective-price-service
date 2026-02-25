package com.selection.effectivepriceservice.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.selection.effectivepriceservice.application.port.PriceRepositoryPort;
import com.selection.effectivepriceservice.domain.exception.EffectivePriceNotFoundException;
import com.selection.effectivepriceservice.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetEffectivePriceUseCaseTest {

  @Test
  @DisplayName("Should return effective price when repository returns matching prices")
  void shouldReturnEffectivePrice() {

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

    PriceRepositoryPort stubRepository = (brandId, productId) -> List.of(lowPriority, highPriority);

    var useCase = new GetEffectivePriceUseCase(stubRepository);

    var result = useCase.execute(1L, 35455L, applicationDate);

    assertThat(result).isEqualTo(highPriority);
  }

  @Test
  @DisplayName("Should throw exception when no effective price is found")
  void shouldThrowWhenNoPriceFound() {

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

    PriceRepositoryPort stubRepository = (brandId, productId) -> List.of(price);

    var useCase = new GetEffectivePriceUseCase(stubRepository);

    assertThatThrownBy(() -> useCase.execute(1L, 35455L, applicationDate))
        .isInstanceOf(EffectivePriceNotFoundException.class)
        .hasMessageContaining("No effective price found");
  }

  @Test
  @DisplayName("Should propagate repository data correctly to selector")
  void shouldDelegateToSelector() {

    var applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

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

    PriceRepositoryPort stubRepository = (brandId, productId) -> List.of(price);

    var useCase = new GetEffectivePriceUseCase(stubRepository);

    var result = useCase.execute(1L, 35455L, applicationDate);

    assertThat(result.priceList()).isEqualTo(1);
  }
}
