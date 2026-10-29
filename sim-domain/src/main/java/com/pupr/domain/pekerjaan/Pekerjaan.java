package com.pupr.domain.pekerjaan;

import java.math.BigDecimal;
import java.util.Date;

public record Pekerjaan(
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
        String dpaValue) {
}
