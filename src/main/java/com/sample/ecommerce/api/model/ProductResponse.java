package com.sample.ecommerce.api.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
  private Integer productId;
  private Integer accountId;
  private String merchantName;
  private String productCode;
  private String productName;
  private String productDescr;
  private BigDecimal price;
}