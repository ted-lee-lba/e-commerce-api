package com.sample.ecommerce.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderProductDTO {
	private Integer productId;
	private Integer quantity;
}
