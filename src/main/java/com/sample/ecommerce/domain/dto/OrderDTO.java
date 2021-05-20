package com.sample.ecommerce.domain.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
  private Integer orderId;
  private Integer buyerId;
  private Integer merchantId;
  private Timestamp orderTime;
}