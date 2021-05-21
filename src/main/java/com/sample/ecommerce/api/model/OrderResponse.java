package com.sample.ecommerce.api.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	private Integer orderId;
	private Integer buyerId;
	private Integer merchantId;
	private String merchantName;
	private Timestamp orderedTime;
	private BigDecimal orderedTotal;
	private Integer orderedQuantity;
	@Builder.Default
	private List<OrderProductResponse> products = new ArrayList<>();
}