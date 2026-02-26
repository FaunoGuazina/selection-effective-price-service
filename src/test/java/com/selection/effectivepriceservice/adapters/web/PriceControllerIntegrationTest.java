package com.selection.effectivepriceservice.adapters.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @ParameterizedTest(name = "applicationDateTime={0} should return priceList={1} and price={2}")
  @CsvSource({
    "2020-06-14T10:00:00, 1, 35.50, 2020-06-14T00:00:00, 2020-12-31T23:59:59",
    "2020-06-14T16:00:00, 2, 25.45, 2020-06-14T15:00:00, 2020-06-14T18:30:00",
    "2020-06-14T21:00:00, 1, 35.50, 2020-06-14T00:00:00, 2020-12-31T23:59:59",
    "2020-06-15T10:00:00, 3, 30.50, 2020-06-15T00:00:00, 2020-06-15T11:00:00",
    "2020-06-16T21:00:00, 4, 38.95, 2020-06-15T16:00:00, 2020-12-31T23:59:59"
  })
  @DisplayName("Should return correct effective price for official scenarios")
  void shouldReturnExpectedPriceForOfficialScenarios(
      String applicationDateTime,
      int expectedPriceList,
      double expectedPrice,
      String expectedStartDate,
      String expectedEndDate)
      throws Exception {

    mockMvc
        .perform(
            get("/api/prices/effective")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDateTime", applicationDateTime))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.priceList").value(expectedPriceList))
        .andExpect(jsonPath("$.price").value(expectedPrice))
        .andExpect(jsonPath("$.currency").value("EUR"))
        .andExpect(jsonPath("$.startDate").value(expectedStartDate))
        .andExpect(jsonPath("$.endDate").value(expectedEndDate));
  }

  @Test
  @DisplayName("Should return 404 when no effective price exists")
  void shouldReturnNotFound() throws Exception {

    mockMvc
        .perform(
            get("/api/prices/effective")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDateTime", "2019-01-01T10:00:00"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should return 400 when date format is invalid")
  void shouldReturnBadRequestForInvalidDate() throws Exception {

    mockMvc
        .perform(
            get("/api/prices/effective")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDateTime", "invalid-date"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return 400 when brandId is missing")
  void shouldReturnBadRequestWhenBrandIdMissing() throws Exception {

    mockMvc
        .perform(
            get("/api/prices/effective")
                .param("productId", "35455")
                .param("applicationDateTime", "2020-06-14T10:00:00"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return 400 when productId is missing")
  void shouldReturnBadRequestWhenProductIdMissing() throws Exception {

    mockMvc
        .perform(
            get("/api/prices/effective")
                .param("brandId", "1")
                .param("applicationDateTime", "2020-06-14T10:00:00"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return 400 when applicationDateTime is missing")
  void shouldReturnBadRequestWhenApplicationDateTimeMissing() throws Exception {

    mockMvc
        .perform(get("/api/prices/effective").param("brandId", "1").param("productId", "35455"))
        .andExpect(status().isBadRequest());
  }
}
