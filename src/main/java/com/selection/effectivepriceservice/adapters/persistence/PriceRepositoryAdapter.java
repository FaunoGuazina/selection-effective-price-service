package com.selection.effectivepriceservice.adapters.persistence;

import com.selection.effectivepriceservice.adapters.persistence.mapper.PricePersistenceMapper;
import com.selection.effectivepriceservice.adapters.persistence.repository.PriceRepository;
import com.selection.effectivepriceservice.application.port.PriceRepositoryPort;
import com.selection.effectivepriceservice.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

  private final PriceRepository repository;
  private final PricePersistenceMapper mapper;

  @Override
  public Optional<Price> findEffectivePrice(
      Long brandId, Long productId, LocalDateTime applicationDate) {
    return repository.findEffectivePrice(brandId, productId, applicationDate).map(mapper::toDomain);
  }
}
