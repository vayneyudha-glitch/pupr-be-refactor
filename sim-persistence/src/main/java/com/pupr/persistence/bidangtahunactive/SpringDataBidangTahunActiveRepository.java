package com.pupr.persistence.bidangtahunactive;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataBidangTahunActiveRepository
        extends JpaRepository<BidangTahunActiveJpaEntity, Long> {

    Optional<BidangTahunActiveJpaEntity> findByBidangDesc(String bidangDesc);
}
