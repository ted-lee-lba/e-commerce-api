package com.sample.ecommerce.api.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	private Integer buyerId;

	@Builder.Default
	private List<OrderProductRequest> products = new ArrayList<>();
}