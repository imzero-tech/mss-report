package com.example.mss.configuration.persistence;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Configuration
public class ModelMapperConfiguration {
    /**
     * MatchingStrategies.STANDARD - 순서 상관 없이, destination 모든 속성 일치/ source 속성 이름은 하나 이상 일치
     * MatchingStrategies.LOOSE - 순서 상관 없이 마지막 destination 모든 속성 일치/ 마지막 source 속성 이름은 하나 이상 일치
     * MatchingStrategies.STRICT - 엄격하게 순서도 일치 해야 한다. destination 모든 속성 일치/ source 속성 이름은 하나 이상 일치
     * @return
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }
}
