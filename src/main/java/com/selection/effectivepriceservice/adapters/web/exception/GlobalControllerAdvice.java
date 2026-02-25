package com.selection.effectivepriceservice.adapters.web.exception;

import com.selection.effectivepriceservice.domain.exception.EffectivePriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(EffectivePriceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleNotFound() {
    log.error("Effective price not found");
  }
}
