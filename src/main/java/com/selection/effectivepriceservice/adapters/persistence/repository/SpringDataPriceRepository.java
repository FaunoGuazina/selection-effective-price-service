package com.selection.effectivepriceservice.adapters.persistence.repository;

import com.selection.effectivepriceservice.adapters.persistence.entity.PriceJpaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPriceRepository extends JpaRepository<PriceJpaEntity, Long> {

  List<PriceJpaEntity> findByBrandIdAndProductId(Long brandId, Long productId);
}
