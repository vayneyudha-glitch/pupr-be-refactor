package com.pupr.domain.bap;

import com.pupr.domain.master.Master;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bap {

    private Long id;
    private Long masterTransactionId;
    private String bapNo;
    private Date bapDate;
    private String bapDateText;
    private BigDecimal bapAmount;
    private BigDecimal contrackAmount;
    private String paymentMethodId;
    private String paymentMethodDesc;
    private String hibahNo;
    private Date hibahDate;
    private String namePengajuan;
    private String phoneNumberPengajuan;
    private Pengajuan pengajuan;
    private Boolean validItemanis;
    private Master master;
    private Map<String, Object> ringkasan;
    private Map<String, Object> kwitansi;
    private Map<String, Object> spp;
    private Map<String, Object> sp2D;
    private Map<String, Object> spm;
    private Map<String, Object> validasi;
    private Map<String, Object> rekom;
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
