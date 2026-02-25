package com.selection.effectivepriceservice.adapters.persistence.mapper;

import com.selection.effectivepriceservice.adapters.persistence.entity.PriceJpaEntity;
import com.selection.effectivepriceservice.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PricePersistenceMapper {

  Price toDomain(PriceJpaEntity entity);
}
