package com.sample.ecommerce.domain.dto;

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
public class OrderDTO {
	private Integer orderId;
	private Integer buyerId;
	private Integer merchantId;
	private Timestamp orderTime;

	@Builder.Default
	private List<OrderProductDTO> products = new ArrayList<>();
}