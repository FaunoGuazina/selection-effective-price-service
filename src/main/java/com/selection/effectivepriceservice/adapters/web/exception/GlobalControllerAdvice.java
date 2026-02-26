package com.selection.effectivepriceservice.adapters.web.exception;

import com.selection.effectivepriceservice.domain.exception.EffectivePriceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

  public static final String ERROR_CODE_PROPERTY = "errorCode";

  @ExceptionHandler(EffectivePriceNotFoundException.class)
  public ProblemDetail handleEffectivePriceNotFoundException(
      EffectivePriceNotFoundException ex, HttpServletRequest request) {

    log.warn("Effective price not found: {}", ex.getMessage());

    var problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

    problem.setTitle("Effective price not found");
    problem.setDetail(ex.getMessage());

    problem.setType(URI.create("https://api.yourdomain.com/errors/price-not-found"));

    problem.setInstance(URI.create(request.getRequestURI()));

    problem.setProperty(ERROR_CODE_PROPERTY, "PRICE_NOT_FOUND");

    return problem;
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ProblemDetail handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {

    log.warn("Invalid request parameter: {}", ex.getMessage());

    var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problem.setTitle("Invalid request parameter");
    problem.setDetail(
        "Parameter '%s' has invalid value '%s'".formatted(ex.getName(), ex.getValue()));
    problem.setProperty(ERROR_CODE_PROPERTY, "INVALID_PARAMETER");

    return problem;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ProblemDetail handleConstraintViolation(
      ConstraintViolationException ex, HttpServletRequest request) {

    log.warn("Constraint violation: {}", ex.getMessage());

    var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

    problem.setTitle("Validation error");
    problem.setDetail("One or more request parameters are invalid");

    problem.setType(URI.create("https://api.yourdomain.com/errors/validation-error"));
    problem.setInstance(URI.create(request.getRequestURI()));

    problem.setProperty(
        "violations",
        ex.getConstraintViolations().stream()
            .map(v -> "%s: %s".formatted(v.getPropertyPath(), v.getMessage()))
            .toList());

    problem.setProperty(ERROR_CODE_PROPERTY, "VALIDATION_ERROR");

    return problem;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpServletRequest request) {

    log.warn("Method argument not valid: {}", ex.getMessage());

    var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

    problem.setTitle("Validation error");
    problem.setDetail("Request body validation failed");

    problem.setType(URI.create("https://api.yourdomain.com/errors/body-validation-error"));
    problem.setInstance(URI.create(request.getRequestURI()));

    problem.setProperty(
        "errors",
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage()))
            .toList());

    problem.setProperty(ERROR_CODE_PROPERTY, "BODY_VALIDATION_ERROR");

    return problem;
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ProblemDetail handleMissingParameter(
      MissingServletRequestParameterException ex, HttpServletRequest request) {

    var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problem.setTitle("Missing required parameter");
    problem.setDetail("Parameter '%s' is required".formatted(ex.getParameterName()));
    problem.setType(URI.create("https://api.yourdomain.com/errors/missing-parameter"));
    problem.setInstance(URI.create(request.getRequestURI()));
    problem.setProperty(ERROR_CODE_PROPERTY, "MISSING_PARAMETER");

    return problem;
  }
}
