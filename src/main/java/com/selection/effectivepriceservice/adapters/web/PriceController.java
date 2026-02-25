package com.selection.effectivepriceservice.adapters.web;

import com.selection.effectivepriceservice.adapters.web.dto.EffectivePriceResponse;
import com.selection.effectivepriceservice.adapters.web.mapper.PriceWebMapper;
import com.selection.effectivepriceservice.application.usecase.GetEffectivePriceUseCase;
import com.selection.effectivepriceservice.domain.exception.EffectivePriceNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

  private final GetEffectivePriceUseCase useCase;
  private final PriceWebMapper mapper;

  @GetMapping("/effective")
  public EffectivePriceResponse getEffectivePrice(
      @RequestParam Long brandId,
      @RequestParam Long productId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime applicationDateTime) {

    var price = useCase.execute(brandId, productId, applicationDateTime);

    return mapper.toResponse(price);
  }

  @ExceptionHandler(EffectivePriceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleNotFound() {
    // Intentionally empty → 404 returned
  }
}
