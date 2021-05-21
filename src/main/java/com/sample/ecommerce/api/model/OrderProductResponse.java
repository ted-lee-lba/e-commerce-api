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
public class OrderProductResponse {
	private Integer order_product_id;
	private Integer orderId;
	private String productCode;
	private String productDescr;
	private BigDecimal price;
	private Integer quantity;
}