package com.pupr.web.kwitansi;

import com.pupr.application.kwitansi.KwitansiUseCase;
import com.pupr.domain.bap.BapRepository;
import com.pupr.domain.bidangtahunactive.BidangTahunActiveRepository;
import com.pupr.domain.deletedcollection.DeletedCollectionRepository;
import com.pupr.domain.kwitansi.KwitansiRepository;
import com.pupr.domain.master.MasterRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KwitansiUseCaseConfiguration {

    @Bean
    KwitansiUseCase kwitansiUseCase(
            KwitansiRepository kwitansiRepository,
            BapRepository bapRepository,
            MasterRepository masterRepository,
            BidangTahunActiveRepository bidangTahunActiveRepository,
            DeletedCollectionRepository deletedCollectionRepository) {
        return new KwitansiUseCase(
                kwitansiRepository,
                bapRepository,
                masterRepository,
                bidangTahunActiveRepository,
                deletedCollectionRepository);
    }
}
