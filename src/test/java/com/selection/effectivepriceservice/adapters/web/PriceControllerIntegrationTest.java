package com.selection.effectivepriceservice.adapters.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Should return effective price for valid request")
  void shouldReturnEffectivePrice() throws Exception {

    mockMvc.perform(
            get("/api/prices/effective")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDateTime", "2020-06-14T16:00:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.brandId").value(1))
        .andExpect(jsonPath("$.productId").value(35455))
        .andExpect(jsonPath("$.priceList").value(2))
        .andExpect(jsonPath("$.price").value(25.45))
        .andExpect(jsonPath("$.currency").value("EUR"));
  }

  @Test
  @DisplayName("Should return 404 when no effective price exists")
  void shouldReturnNotFound() throws Exception {

    mockMvc.perform(
            get("/api/prices/effective")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDateTime", "2019-01-01T10:00:00"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should return 400 when date format is invalid")
  void shouldReturnBadRequestForInvalidDate() throws Exception {

    mockMvc.perform(
            get("/api/prices/effective")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDateTime", "invalid-date"))
        .andExpect(status().isBadRequest());
  }
}

