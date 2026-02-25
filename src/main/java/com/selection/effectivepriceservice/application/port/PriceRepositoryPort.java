package com.selection.effectivepriceservice.application.port;

import com.selection.effectivepriceservice.domain.model.Price;
import java.util.List;

public interface PriceRepositoryPort {

  List<Price> findByBrandIdAndProductId(Long brandId, Long productId);
}
