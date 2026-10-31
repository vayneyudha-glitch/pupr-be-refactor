package com.pupr.web.master;

import com.pupr.application.master.MasterMapper;
import com.pupr.application.master.MasterUseCase;
import com.pupr.domain.master.MasterRepository;
import com.pupr.domain.pekerjaan.PekerjaanSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MasterUseCaseConfiguration {

    @Bean
    MasterUseCase masterUseCase(MasterRepository masterRepository, PekerjaanSource pekerjaanSource) {
        return new MasterUseCase(masterRepository, pekerjaanSource, MasterMapper.INSTANCE);
    }
}
