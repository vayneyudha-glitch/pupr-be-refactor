package com.pupr.domain.kwitansi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kwitansi {

    private Long id;
    private String bapId;
    private Long masterTransactionId;
    private String bapNo;
    private String description;
    private String kepalaBidangNo;
    private String kepalaBidangName;
    private String ppkNo;
    private String ppkName;
    private String pptkNo;
    private String pptkName;
    private String bppNo;
    private String bppName;
    private String direkturNo;
    private String direkturName;
    private Date kwitansiDate;
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
