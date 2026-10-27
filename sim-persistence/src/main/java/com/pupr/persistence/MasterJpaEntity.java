package com.pupr.persistence;

import com.pupr.domain.Master;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Entity
@Table(name = "master_transaction")
public class MasterJpaEntity {

    private static final DateTimeFormatter API_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bidang;
    private String anggaran;
    private String kategory;
    @Column(name = "kode_program")
    private String kodeProgram;
    @Column(name = "program_desc")
    private String programDesc;
    @Column(name = "kode_kegiatan")
    private String kodeKegiatan;
    @Column(name = "kegiatan_desc")
    private String kegiatanDesc;
    @Column(name = "kode_sub_kegiatan")
    private String kodeSubKegiatan;
    @Column(name = "sub_kegitan_desc")
    private String subKegitanDesc;
    @Column(name = "kode_belanja")
    private String kodeBelanja;
    @Column(name = "belanja_desc")
    private String belanjaDesc;
    private String pekerjaan;
    @Column(name = "pekerjaan_id")
    private String pekerjaanId;
    @Column(name = "no_dpa")
    private String noDpa;
    @Column(name = "tgl_dpa")
    private LocalDate tglDpa;
    @Column(name = "nilai_dpa")
    private BigDecimal nilaiDpa;
    private Integer tahun;
    private Integer no;
    @Column(name = "spk_no")
    private String spkNo;
    @Column(name = "nilai_spk")
    private BigDecimal nilaiSpk;
    @Column(name = "tgl_spk")
    private LocalDate tglSpk;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tgl_spk_form")
    private Date tglSpkForm;
    private String version;
    @Column(name = "tgl_start_pekerjaan")
    private LocalDate tglStartPekerjaan;
    @Column(name = "tgl_end_pekerjaan")
    private LocalDate tglEndPekerjaan;
    @Column(name = "mitra_id")
    private String mitraId;
    @Column(name = "mitra_name")
    private String mitraName;
    @Column(name = "mitra_pimpinan")
    private String mitraPimpinan;
    @Column(name = "mitra_no_rekening")
    private String mitraNoRekening;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private String createdDate;
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "last_update")
    private String lastUpdate;
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
    @Column(name = "deleted_by")
    private String deletedBy;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "deleted_date")
    private String deletedDate;
    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    protected MasterJpaEntity() {
    }

    public static MasterJpaEntity fromDomain(Master master) {
        MasterJpaEntity entity = new MasterJpaEntity();
        entity.id = master.getId();
        entity.bidang = master.getBidang();
        entity.anggaran = master.getAnggaran();
        entity.kategory = master.getKategory();
        entity.kodeProgram = master.getKodeProgram();
        entity.programDesc = master.getProgramDesc();
        entity.kodeKegiatan = master.getKodeKegiatan();
        entity.kegiatanDesc = master.getKegiatanDesc();
        entity.kodeSubKegiatan = master.getKodeSubKegiatan();
        entity.subKegitanDesc = master.getSubKegitanDesc();
        entity.kodeBelanja = master.getKodeBelanja();
        entity.belanjaDesc = master.getBelanjaDesc();
        entity.pekerjaan = master.getPekerjaan();
        entity.pekerjaanId = master.getPekerjaanId();
        entity.noDpa = master.getNoDpa();
        entity.tglDpa = parseDate(master.getTglDpa());
        entity.nilaiDpa = master.getNilaiDpa();
        entity.tahun = master.getTahun();
        entity.no = master.getNo();
        entity.spkNo = master.getSpkNo();
        entity.nilaiSpk = master.getNilaiSpk();
        entity.tglSpk = parseDate(master.getTglSpk());
        entity.tglSpkForm = master.getTglSpkForm();
        entity.version = master.getVersion();
        entity.tglStartPekerjaan = parseDate(master.getTglStartPekerjaan());
        entity.tglEndPekerjaan = parseDate(master.getTglEndPekerjaan());
        entity.mitraId = master.getMitraId();
        entity.mitraName = master.getMitraName();
        entity.mitraPimpinan = master.getMitraPimpinan();
        entity.mitraNoRekening = master.getMitraNoRekening();
        entity.createdBy = master.getCreatedBy();
        entity.createdDate = master.getCreatedDate();
        entity.createdTime = master.getCreatedTime();
        entity.updateBy = master.getUpdateBy();
        entity.lastUpdate = master.getLastUpdate();
        entity.lastUpdateTime = master.getLastUpdateTime();
        entity.deletedBy = master.getDeletedBy();
        entity.isDeleted = master.getIsDeleted();
        entity.deletedDate = master.getDeletedDate();
        entity.deletedTime = master.getDeletedTime();
        return entity;
    }

    public Master toDomain() {
        return Master.builder()
                .id(id)
                .bidang(bidang)
                .anggaran(anggaran)
                .kategory(kategory)
                .kodeProgram(kodeProgram)
                .programDesc(programDesc)
                .kodeKegiatan(kodeKegiatan)
                .kegiatanDesc(kegiatanDesc)
                .kodeSubKegiatan(kodeSubKegiatan)
                .subKegitanDesc(subKegitanDesc)
                .kodeBelanja(kodeBelanja)
                .belanjaDesc(belanjaDesc)
                .pekerjaan(pekerjaan)
                .pekerjaanId(pekerjaanId)
                .noDpa(noDpa)
                .tglDpa(formatDate(tglDpa))
                .nilaiDpa(nilaiDpa)
                .tahun(tahun)
                .no(no)
                .spkNo(spkNo)
                .nilaiSpk(nilaiSpk)
                .tglSpk(formatDate(tglSpk))
                .tglSpkForm(tglSpkForm)
                .version(version)
                .tglStartPekerjaan(formatDate(tglStartPekerjaan))
                .tglEndPekerjaan(formatDate(tglEndPekerjaan))
                .mitraId(mitraId)
                .mitraName(mitraName)
                .mitraPimpinan(mitraPimpinan)
                .mitraNoRekening(mitraNoRekening)
                .createdBy(createdBy)
                .createdDate(createdDate)
                .createdTime(createdTime)
                .updateBy(updateBy)
                .lastUpdate(lastUpdate)
                .lastUpdateTime(lastUpdateTime)
                .deletedBy(deletedBy)
                .isDeleted(isDeleted)
                .deletedDate(deletedDate)
                .deletedTime(deletedTime)
                .build();
    }

    private static LocalDate parseDate(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(value, API_DATE);
        } catch (DateTimeParseException ignored) {
            return LocalDate.parse(value, ISO_DATE);
        }
    }

    private static String formatDate(LocalDate value) {
        return value == null ? null : value.format(API_DATE);
    }
}
