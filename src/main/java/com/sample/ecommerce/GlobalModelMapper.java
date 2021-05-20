package com.sample.ecommerce;

import org.modelmapper.ModelMapper;
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
				.setDestinationNamingConvention(NamingConventions.NONE);
	}
    
}
