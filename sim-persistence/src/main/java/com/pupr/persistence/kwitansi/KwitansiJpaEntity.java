package com.pupr.persistence.kwitansi;

import com.pupr.domain.kwitansi.Kwitansi;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "kwitansi_transaction")
public class KwitansiJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bap_id")
    private String bapId;
    @Column(name = "master_transaction_id")
    private Long masterTransactionId;
    @Column(name = "bap_no")
    private String bapNo;
    private String description;
    @Column(name = "kepala_bidang_no")
    private String kepalaBidangNo;
    @Column(name = "kepala_bidang_name")
    private String kepalaBidangName;
    @Column(name = "ppk_no")
    private String ppkNo;
    @Column(name = "ppk_name")
    private String ppkName;
    @Column(name = "pptk_no")
    private String pptkNo;
    @Column(name = "pptk_name")
    private String pptkName;
    @Column(name = "bpp_no")
    private String bppNo;
    @Column(name = "bpp_name")
    private String bppName;
    @Column(name = "direktur_no")
    private String direkturNo;
    @Column(name = "direktur_name")
    private String direkturName;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "kwitansi_date")
    private Date kwitansiDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "last_update")
    private LocalDate lastUpdate;
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
    @Column(name = "deleted_by")
    private String deletedBy;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "deleted_date")
    private LocalDate deletedDate;
    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    protected KwitansiJpaEntity() {
    }

    public static KwitansiJpaEntity fromDomain(Kwitansi kwitansi) {
        KwitansiJpaEntity entity = new KwitansiJpaEntity();
        entity.id = kwitansi.getId();
        entity.bapId = kwitansi.getBapId();
        entity.masterTransactionId = kwitansi.getMasterTransactionId();
        entity.bapNo = kwitansi.getBapNo();
        entity.description = kwitansi.getDescription();
        entity.kepalaBidangNo = kwitansi.getKepalaBidangNo();
        entity.kepalaBidangName = kwitansi.getKepalaBidangName();
        entity.ppkNo = kwitansi.getPpkNo();
        entity.ppkName = kwitansi.getPpkName();
        entity.pptkNo = kwitansi.getPptkNo();
        entity.pptkName = kwitansi.getPptkName();
        entity.bppNo = kwitansi.getBppNo();
        entity.bppName = kwitansi.getBppName();
        entity.direkturNo = kwitansi.getDirekturNo();
        entity.direkturName = kwitansi.getDirekturName();
        entity.kwitansiDate = kwitansi.getKwitansiDate();
        entity.createdBy = kwitansi.getCreatedBy();
        entity.createdDate = parseDate(kwitansi.getCreatedDate());
        entity.createdTime = kwitansi.getCreatedTime();
        entity.updateBy = kwitansi.getUpdateBy();
        entity.lastUpdate = parseDate(kwitansi.getLastUpdate());
        entity.lastUpdateTime = kwitansi.getLastUpdateTime();
        entity.deletedBy = kwitansi.getDeletedBy();
        entity.isDeleted = kwitansi.getIsDeleted();
        entity.deletedDate = parseDate(kwitansi.getDeletedDate());
        entity.deletedTime = kwitansi.getDeletedTime();
        return entity;
    }

    public Kwitansi toDomain() {
        return Kwitansi.builder()
                .id(id)
                .bapId(bapId)
                .masterTransactionId(masterTransactionId)
                .bapNo(bapNo)
                .description(description)
                .kepalaBidangNo(kepalaBidangNo)
                .kepalaBidangName(kepalaBidangName)
                .ppkNo(ppkNo)
                .ppkName(ppkName)
                .pptkNo(pptkNo)
                .pptkName(pptkName)
                .bppNo(bppNo)
                .bppName(bppName)
                .direkturNo(direkturNo)
                .direkturName(direkturName)
                .kwitansiDate(kwitansiDate)
                .createdBy(createdBy)
                .createdDate(formatDate(createdDate))
                .createdTime(createdTime)
                .updateBy(updateBy)
                .lastUpdate(formatDate(lastUpdate))
                .lastUpdateTime(lastUpdateTime)
                .deletedBy(deletedBy)
                .isDeleted(isDeleted)
                .deletedDate(formatDate(deletedDate))
                .deletedTime(deletedTime)
                .build();
    }

    private static LocalDate parseDate(String value) {
        return value == null ? null : LocalDate.parse(value.substring(0, 10));
    }

    private static String formatDate(LocalDate value) {
        return value == null ? null : value.toString();
    }
}
