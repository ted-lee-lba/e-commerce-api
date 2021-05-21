package com.sample.ecommerce.domain.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {
  private Integer orderProductId;
  private Integer orderId;
  private String productCode;
  private String productDescr;
  private BigDecimal price;
  private Integer quantity;
}