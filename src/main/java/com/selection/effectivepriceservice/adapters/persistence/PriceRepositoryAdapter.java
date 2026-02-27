package com.selection.effectivepriceservice.adapters.persistence;

import com.selection.effectivepriceservice.adapters.persistence.mapper.PricePersistenceMapper;
import com.selection.effectivepriceservice.adapters.persistence.repository.PriceRepository;
import com.selection.effectivepriceservice.application.port.PriceRepositoryPort;
import com.selection.effectivepriceservice.domain.model.Price;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

  private final PriceRepository repository;
  private final PricePersistenceMapper mapper;

  @Override
  public List<Price> findPricesByBrandProductAndDate(
      Long brandId, Long productId, LocalDateTime applicationDate) {
    return repository.findPricesByBrandProductAndDate(brandId, productId, applicationDate).stream()
        .map(mapper::toDomain)
        .toList();
  }
}
