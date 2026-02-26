package com.selection.effectivepriceservice.adapters.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.selection.effectivepriceservice.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PriceRepositoryAdapterIntegrationTest {

  @Autowired private PriceRepositoryAdapter adapter;

  @Test
  @DisplayName("Should retrieve prices from H2 and map them to domain")
  void shouldRetrieveAndMapPrices() {

    Optional<Price> optionalPrice =
        adapter.findEffectivePrice(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));

    assertThat(optionalPrice).isPresent();

    Price first = optionalPrice.get();

    assertThat(first.brandId()).isEqualTo(1L);
    assertThat(first.productId()).isEqualTo(35455L);
    assertThat(first.currency()).isEqualTo("EUR");
  }
}
