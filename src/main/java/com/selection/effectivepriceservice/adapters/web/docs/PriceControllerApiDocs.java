package com.selection.effectivepriceservice.adapters.web.docs;

import com.selection.effectivepriceservice.adapters.web.dto.EffectivePriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.experimental.UtilityClass;
import org.springframework.http.ProblemDetail;

@UtilityClass
public final class PriceControllerApiDocs {

  /** Controller-level OpenAPI documentation for PriceController. */
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Tag(
      name = "Prices",
      description =
          "Operations related to price retrieval and effective price calculation based on date ranges and priority rules.")
  public @interface PriceControllerApiDoc {}

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
              + "The applicationDateTime must follow ISO-8601 format (yyyy-MM-dd'T'HH:mm:ss). "
              + "If multiple prices match the date range, the one with the highest priority is selected.")
  @Parameter(
      name = "brandId",
      description = "Identifier of the brand",
      required = true,
      in = ParameterIn.QUERY,
      example = "1")
  @Parameter(
      name = "productId",
      description = "Identifier of the product",
      required = true,
      in = ParameterIn.QUERY,
      example = "35455")
  @Parameter(
      name = "applicationDateTime",
      description = "Application date-time in ISO-8601 format (yyyy-MM-dd'T'HH:mm:ss)",
      required = true,
      in = ParameterIn.QUERY,
      example = "2020-06-14T10:00:00")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Effective price retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(
                            implementation = EffectivePriceResponse.class,
                            example =
                                """
              {
                "brandId": 1,
                "productId": 35455,
                "priceList": 1,
                "startDate": "2020-06-14T00:00:00",
                "endDate": "2020-12-31T23:59:59",
                "price": 35.50,
                "currency": "EUR"
              }
              """))),
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
