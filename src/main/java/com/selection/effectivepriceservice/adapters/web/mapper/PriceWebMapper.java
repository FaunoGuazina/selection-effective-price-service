package com.selection.effectivepriceservice.adapters.web.mapper;

import com.selection.effectivepriceservice.adapters.web.dto.EffectivePriceResponse;
import com.selection.effectivepriceservice.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PriceWebMapper {

  EffectivePriceResponse toResponse(Price price);
}
