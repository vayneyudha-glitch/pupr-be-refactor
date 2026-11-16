package com.pupr.application.kwitansi;

import java.time.LocalDateTime;
import java.util.Date;

public record KwitansiDto(
        String id,
        String bapId,
        String masterTransactionId,
        String bapNo,
        String description,
        String kepalaBidangNo,
        String kepalaBidangName,
        String ppkNo,
        String ppkName,
        String pptkNo,
        String pptkName,
        String bppNo,
        String bppName,
        String direkturNo,
        String direkturName,
        Date kwitansiDate,
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
