package com.pupr.application.bap;

import com.pupr.application.master.MasterDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public record BapDto(
        String id,
        String masterTransactionId,
        String bapNo,
        Date bapDate,
        String bapDateText,
        BigDecimal bapAmount,
        BigDecimal contrackAmount,
        String paymentMethodId,
        String paymentMethodDesc,
        String hibahNo,
        Date hibahDate,
        String namePengajuan,
        String phoneNumberPengajuan,
        PengajuanDto pengajuan,
        Boolean validItemanis,
        MasterDto master,
        Map<String, Object> ringkasan,
        Map<String, Object> kwitansi,
        Map<String, Object> spp,
        Map<String, Object> sp2D,
        Map<String, Object> spm,
        Map<String, Object> validasi,
        Map<String, Object> rekom,
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

    public static BapDto empty() {
        return new BapDto(
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null);
    }
}
