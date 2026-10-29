package com.pupr.application.master;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record MasterDto(
        String id,
        String bidang,
        String anggaran,
        String kategory,
        String kodeProgram,
        String programDesc,
        String kodeKegiatan,
        String kegiatanDesc,
        String kodeSubKegiatan,
        String subKegitanDesc,
        String kodeBelanja,
        String belanjaDesc,
        String pekerjaan,
        String pekerjaanId,
        String noDpa,
        String tglDpa,
        BigDecimal nilaiDpa,
        Integer tahun,
        Integer no,
        String spkNo,
        BigDecimal nilaiSpk,
        String tglSpk,
        Date tglSpkForm,
        String version,
        String tglStartPekerjaan,
        String tglEndPekerjaan,
        String mitraId,
        String mitraName,
        String mitraPimpinan,
        String mitraNoRekening,
        String createdBy,
        String createdDate,
        LocalDateTime createdTime,
        String updateBy,
        String lastUpdate,
        LocalDateTime lastUpdateTime,
        String deletedBy,
        Boolean isDeleted,
        String deletedDate,
        LocalDateTime deletedTime) {
}
