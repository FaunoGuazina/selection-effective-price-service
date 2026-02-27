package com.selection.effectivepriceservice.adapters.persistence.repository.impl;

import com.selection.effectivepriceservice.adapters.persistence.entity.PriceJpaEntity;
import com.selection.effectivepriceservice.adapters.persistence.repository.PriceJpaCustomRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PriceJpaCustomRepositoryImpl implements PriceJpaCustomRepository {

  private final EntityManager entityManager;

  @Override
  public List<PriceJpaEntity> findPricesByBrandProductAndDate(
      Long brandId, Long productId, LocalDateTime applicationDate) {

    final String qlString =
        """
            SELECT p
            FROM PriceJpaEntity p
            WHERE p.brandId = :brandId
              AND p.productId = :productId
              AND p.startDate <= :applicationDate
                  AND p.endDate >= :applicationDate
            ORDER BY p.priority DESC, p.priceList DESC
        """;

    var query = entityManager.createQuery(qlString, PriceJpaEntity.class);

    query.setParameter("brandId", brandId);
    query.setParameter("productId", productId);
    query.setParameter("applicationDate", applicationDate);

    return query.getResultList();
  }
}
