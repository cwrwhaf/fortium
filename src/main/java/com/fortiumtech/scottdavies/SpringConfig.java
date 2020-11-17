package com.fortiumtech.scottdavies;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

@Configuration
public class SpringConfig {

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean
	  public SpelAwareProxyProjectionFactory projectionFactory() {
	    return new SpelAwareProxyProjectionFactory();
	  }
}
