package com.pupr.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Master {

    private Long id;
    private String bidang;
    private String anggaran;
    private String kategory;
    private String kodeProgram;
    private String programDesc;
    private String kodeKegiatan;
    private String kegiatanDesc;
    private String kodeSubKegiatan;
    private String subKegitanDesc;
    private String kodeBelanja;
    private String belanjaDesc;
    private String pekerjaan;
    private String pekerjaanId;
    private String noDpa;
    private String tglDpa;
    private BigDecimal nilaiDpa;
    private Integer tahun;
    private Integer no;
    private String spkNo;
    private BigDecimal nilaiSpk;
    private String tglSpk;
    private Date tglSpkForm;
    private String version;
    private String tglStartPekerjaan;
    private String tglEndPekerjaan;
    private String mitraId;
    private String mitraName;
    private String mitraPimpinan;
    private String mitraNoRekening;
    private String createdBy;
    private String createdDate;
    private LocalDateTime createdTime;
    private String updateBy;
    private String lastUpdate;
    private LocalDateTime lastUpdateTime;
    private String deletedBy;
    private Boolean isDeleted;
    private String deletedDate;
    private LocalDateTime deletedTime;
}
