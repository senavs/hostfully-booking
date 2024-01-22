package com.senavs.booking.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.convention.MatchingStrategies.LOOSE;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(LOOSE);
        return modelMapper;
    }
}
