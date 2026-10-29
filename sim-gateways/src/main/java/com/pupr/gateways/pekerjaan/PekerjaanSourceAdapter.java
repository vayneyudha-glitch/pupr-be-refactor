package com.pupr.gateways.pekerjaan;

import com.pupr.domain.pekerjaan.Pekerjaan;
import com.pupr.domain.pekerjaan.PekerjaanSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PekerjaanSourceAdapter implements PekerjaanSource {

    private final String masterServiceUrl;
    private final RestClient restClient;

    public PekerjaanSourceAdapter(@Value("${pupr.master-service-url}") String masterServiceUrl) {
        this.masterServiceUrl = masterServiceUrl;
        this.restClient = RestClient.create();
    }

    @Override
    public List<Pekerjaan> findByBidangCode(String bidangCode) {
        PekerjaanResponse[] response = restClient.get()
                .uri(masterServiceUrl + "/api/pekerjaan/by-bidang-code/" + bidangCode)
                .retrieve()
                .body(PekerjaanResponse[].class);
        return Arrays.stream(response)
                .map(PekerjaanResponse::toDomain)
                .collect(Collectors.toList());
    }

    private record PekerjaanResponse(
            String bidangCode,
            String anggaranDesc,
            String categoryType,
            String programCode,
            String programDescription,
            String kegiatanCodeRef,
            String kegiatanDescRef,
            String subKegiatanCodeRef,
            String subKegiatanDescRef,
            String belanjaCodeRef,
            String belanjaDescRef,
            String description,
            String noDpa,
            Date dpaDate,
            String dpaValue,
            BigDecimal belanjaValue,
            String tahunAnggaran,
            String id,
            String code,
            Boolean isActive,
            Integer sortOrder,
            String additionalInfo,
            LocalDateTime createdDate,
            LocalDateTime updatedDate,
            String documentMasterId,
            String programCodeRef,
            String programDescRef) {

        private Pekerjaan toDomain() {
            return new Pekerjaan(
                    bidangCode,
                    anggaranDesc,
                    categoryType,
                    programCode,
                    programDescription,
                    kegiatanCodeRef,
                    kegiatanDescRef,
                    subKegiatanCodeRef,
                    subKegiatanDescRef,
                    belanjaCodeRef,
                    belanjaDescRef,
                    description,
                    noDpa,
                    dpaDate,
                    dpaValue);
        }
    }
}
