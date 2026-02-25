package com.selection.effectivepriceservice.adapters.web;

import com.selection.effectivepriceservice.adapters.web.dto.EffectivePriceResponse;
import com.selection.effectivepriceservice.adapters.web.mapper.PriceWebMapper;
import com.selection.effectivepriceservice.application.usecase.GetEffectivePriceUseCase;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

  private final GetEffectivePriceUseCase useCase;
  private final PriceWebMapper mapper;

  @GetMapping("/effective")
  public EffectivePriceResponse getEffectivePrice(
      @RequestParam @NotNull Long brandId,
      @RequestParam @NotNull Long productId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime applicationDateTime) {

    var price = useCase.execute(brandId, productId, applicationDateTime);

    return mapper.toResponse(price);
  }
}
