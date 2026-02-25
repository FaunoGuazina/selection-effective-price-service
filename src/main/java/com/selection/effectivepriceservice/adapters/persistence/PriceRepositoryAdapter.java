package com.selection.effectivepriceservice.adapters.persistence;

import com.selection.effectivepriceservice.adapters.persistence.mapper.PricePersistenceMapper;
import com.selection.effectivepriceservice.adapters.persistence.repository.SpringDataPriceRepository;
import com.selection.effectivepriceservice.application.port.PriceRepositoryPort;
import com.selection.effectivepriceservice.domain.model.Price;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

  private final SpringDataPriceRepository repository;
  private final PricePersistenceMapper mapper;

  @Override
  public List<Price> findByBrandIdAndProductId(Long brandId, Long productId) {

    return repository.findByBrandIdAndProductId(brandId, productId).stream()
        .map(mapper::toDomain)
        .toList();
  }
}
