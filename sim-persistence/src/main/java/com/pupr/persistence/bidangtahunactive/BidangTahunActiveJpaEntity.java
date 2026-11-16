package com.pupr.persistence.bidangtahunactive;

import com.pupr.domain.bidangtahunactive.BidangTahunActive;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bidang_tahun_active")
public class BidangTahunActiveJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bidang_code")
    private String bidangCode;
    @Column(name = "bidang_desc")
    private String bidangDesc;
    @Column(name = "tahun_active")
    private String tahunActive;

    protected BidangTahunActiveJpaEntity() {
    }

    public BidangTahunActive toDomain() {
        return new BidangTahunActive(id, bidangCode, bidangDesc, tahunActive);
    }
}
