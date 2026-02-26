package com.selection.effectivepriceservice.adapters.persistence.repository.impl;

import com.selection.effectivepriceservice.adapters.persistence.entity.PriceJpaEntity;
import com.selection.effectivepriceservice.adapters.persistence.repository.PriceJpaCustomRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PriceJpaCustomRepositoryImpl implements PriceJpaCustomRepository {

  private final EntityManager entityManager;

  @Override
  public Optional<PriceJpaEntity> findEffectivePrice(
      Long brandId, Long productId, LocalDateTime applicationDate) {

    var query =
        entityManager.createQuery(
            """
                    SELECT p
                    FROM PriceJpaEntity p
                    WHERE p.brandId = :brandId
                      AND p.productId = :productId
                      AND p.startDate <= :applicationDate
                          AND p.endDate >= :applicationDate
                    ORDER BY p.priority DESC, p.priceList DESC
                """,
            PriceJpaEntity.class);

    query.setParameter("brandId", brandId);
    query.setParameter("productId", productId);
    query.setParameter("applicationDate", applicationDate);
    query.setMaxResults(1);

    return query.getResultList().stream().findFirst();
  }
}
