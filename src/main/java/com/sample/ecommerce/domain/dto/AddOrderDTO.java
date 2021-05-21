package com.sample.ecommerce.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderDTO {
	private Integer buyerId;
	private List<AddOrderProductDTO> products;
}
