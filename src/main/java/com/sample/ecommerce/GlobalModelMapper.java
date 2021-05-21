package com.sample.ecommerce;

import com.sample.ecommerce.data.mysql.entity.OrderProduct;
import com.sample.ecommerce.domain.dto.OrderProductDTO;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.NamingConventions;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Component
@Data
public class GlobalModelMapper {
	@Setter(value = AccessLevel.NONE)
	private ModelMapper mapper;

	public GlobalModelMapper() {
		mapper = new ModelMapper();
		mapper.getConfiguration().setSourceNamingConvention(NamingConventions.NONE)
				.setDestinationNamingConvention(NamingConventions.NONE)
				.setAmbiguityIgnored(true);

		orderProductMap();
	}

    private void orderProductMap() {
        TypeMap<OrderProduct, OrderProductDTO> typeMap = mapper.createTypeMap(OrderProduct.class, OrderProductDTO.class);
        typeMap.setPostConverter(converter -> {
            OrderProductDTO dest = converter.getDestination();
            OrderProduct src = converter.getSource();
			dest.setOrderId(src.getOrder().getOrderId());
            return dest;
        });
    }
    
}
