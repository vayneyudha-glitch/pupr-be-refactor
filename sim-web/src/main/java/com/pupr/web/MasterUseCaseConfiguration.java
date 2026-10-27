package com.pupr.web;

import com.pupr.application.MasterMapper;
import com.pupr.application.MasterUseCase;
import com.pupr.domain.MasterRepository;
import com.pupr.domain.PekerjaanSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MasterUseCaseConfiguration {

    @Bean
    MasterUseCase masterUseCase(MasterRepository masterRepository, PekerjaanSource pekerjaanSource) {
        return new MasterUseCase(masterRepository, pekerjaanSource, MasterMapper.INSTANCE);
    }
}
