package com.pupr.web.bap;

import com.pupr.application.bap.BapUseCase;
import com.pupr.domain.bap.BapRepository;
import com.pupr.domain.bidangtahunactive.BidangTahunActiveRepository;
import com.pupr.domain.deletedcollection.DeletedCollectionRepository;
import com.pupr.domain.master.MasterRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BapUseCaseConfiguration {

    @Bean
    BapUseCase bapUseCase(
            BapRepository bapRepository,
            MasterRepository masterRepository,
            BidangTahunActiveRepository bidangTahunActiveRepository,
            DeletedCollectionRepository deletedCollectionRepository) {
        return new BapUseCase(
                bapRepository, masterRepository, bidangTahunActiveRepository, deletedCollectionRepository);
    }
}
