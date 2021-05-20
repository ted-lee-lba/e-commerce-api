package com.sample.ecommerce.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
  private Integer productId;
  private Integer accountId;
  private String productCode;
  private String productName;
  private String productDescr;
  private BigDecimal price;
}