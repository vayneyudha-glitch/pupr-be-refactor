package com.pupr.persistence.bap;

import com.pupr.domain.bap.Bap;
import com.pupr.domain.bap.Pengajuan;
import com.pupr.domain.master.Master;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "bap_transaction")
public class BapJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "master_transaction_id")
    private Long masterTransactionId;
    @Column(name = "bap_no")
    private String bapNo;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bap_date")
    private Date bapDate;
    @Column(name = "bap_date_text")
    private LocalDate bapDateText;
    @Column(name = "bap_amount")
    private BigDecimal bapAmount;
    @Column(name = "contrack_amount")
    private BigDecimal contrackAmount;
    @Column(name = "payment_method_id")
    private String paymentMethodId;
    @Column(name = "payment_method_desc")
    private String paymentMethodDesc;
    @Column(name = "hibah_no")
    private String hibahNo;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hibah_date")
    private Date hibahDate;
    @Column(name = "name_pengajuan")
    private String namePengajuan;
    @Column(name = "phone_number_pengajuan")
    private String phoneNumberPengajuan;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "pengajuan", columnDefinition = "jsonb")
    private Map<String, Object> pengajuan;
    @Column(name = "valid_itemanis")
    private Boolean validItemanis;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "ringkasan_ref", columnDefinition = "jsonb")
    private Map<String, Object> ringkasan;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "kwitansi_ref", columnDefinition = "jsonb")
    private Map<String, Object> kwitansi;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "spp_ref", columnDefinition = "jsonb")
    private Map<String, Object> spp;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "sp2d_ref", columnDefinition = "jsonb")
    private Map<String, Object> sp2D;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "spm_ref", columnDefinition = "jsonb")
    private Map<String, Object> spm;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "validasi_ref", columnDefinition = "jsonb")
    private Map<String, Object> validasi;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rekom_ref", columnDefinition = "jsonb")
    private Map<String, Object> rekom;
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
    @Column(name = "payment_no")
    private String paymentNo;

    protected BapJpaEntity() {
    }

    public static BapJpaEntity fromDomain(Bap bap) {
        BapJpaEntity entity = new BapJpaEntity();
        entity.id = bap.getId();
        entity.masterTransactionId = bap.getMasterTransactionId();
        entity.bapNo = bap.getBapNo();
        entity.bapDate = bap.getBapDate();
        entity.bapDateText = parseDate(bap.getBapDateText());
        entity.bapAmount = bap.getBapAmount();
        entity.contrackAmount = bap.getContrackAmount();
        entity.paymentMethodId = bap.getPaymentMethodId();
        entity.paymentMethodDesc = bap.getPaymentMethodDesc();
        entity.hibahNo = bap.getHibahNo();
        entity.hibahDate = bap.getHibahDate();
        entity.namePengajuan = bap.getNamePengajuan();
        entity.phoneNumberPengajuan = bap.getPhoneNumberPengajuan();
        entity.pengajuan = pengajuanMap(bap.getPengajuan());
        entity.validItemanis = bap.getValidItemanis();
        entity.ringkasan = bap.getRingkasan();
        entity.kwitansi = bap.getKwitansi();
        entity.spp = bap.getSpp();
        entity.sp2D = bap.getSp2D();
        entity.spm = bap.getSpm();
        entity.validasi = bap.getValidasi();
        entity.rekom = bap.getRekom();
        entity.createdBy = bap.getCreatedBy();
        entity.createdDate = parseDate(bap.getCreatedDate());
        entity.createdTime = bap.getCreatedTime();
        entity.updateBy = bap.getUpdateBy();
        entity.lastUpdate = parseDate(bap.getLastUpdate());
        entity.lastUpdateTime = bap.getLastUpdateTime();
        entity.deletedBy = bap.getDeletedBy();
        entity.isDeleted = bap.getIsDeleted();
        entity.deletedDate = parseDate(bap.getDeletedDate());
        entity.deletedTime = bap.getDeletedTime();
        return entity;
    }

    public Bap toDomain() {
        return Bap.builder()
                .id(id)
                .masterTransactionId(masterTransactionId)
                .bapNo(bapNo)
                .bapDate(bapDate)
                .bapDateText(formatDate(bapDateText))
                .bapAmount(bapAmount)
                .contrackAmount(contrackAmount)
                .paymentMethodId(paymentMethodId)
                .paymentMethodDesc(paymentMethodDesc)
                .hibahNo(hibahNo)
                .hibahDate(hibahDate)
                .namePengajuan(namePengajuan)
                .phoneNumberPengajuan(phoneNumberPengajuan)
                .pengajuan(toPengajuan(pengajuan))
                .validItemanis(validItemanis)
                .master(masterTransactionId == null ? null : Master.builder().id(masterTransactionId).build())
                .ringkasan(ringkasan)
                .kwitansi(kwitansi)
                .spp(spp)
                .sp2D(sp2D)
                .spm(spm)
                .validasi(validasi)
                .rekom(rekom)
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

    private static Map<String, Object> pengajuanMap(Pengajuan pengajuan) {
        if (pengajuan == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", pengajuan.name());
        map.put("phoneNumber", pengajuan.phoneNumber());
        return map;
    }

    private static Pengajuan toPengajuan(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        return new Pengajuan((String) map.get("name"), (String) map.get("phoneNumber"));
    }

    private static LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException exception) {
            return LocalDate.parse(value.substring(0, 10));
        }
    }

    private static String formatDate(LocalDate value) {
        return value == null ? null : value.toString();
    }
}
