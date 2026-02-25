package com.selection.effectivepriceservice.adapters.web.docs;

import com.selection.effectivepriceservice.adapters.web.dto.EffectivePriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.experimental.UtilityClass;
import org.springframework.http.ProblemDetail;

@UtilityClass
public final class PriceControllerApiDocs {

  /**
   * GET /api/prices/effective
   *
   * <p>Returns the effective price for a product and brand at a given application date.
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Operation(
      summary = "Get effective price",
      description =
          "Returns the effective price for a given brandId, productId and applicationDateTime. "
              + "If multiple prices match the date range, the one with the highest priority is selected.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Effective price retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EffectivePriceResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request parameters",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(
            responseCode = "404",
            description = "No effective price found for the given criteria",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  public @interface GetEffectivePriceApiDoc {}
}
