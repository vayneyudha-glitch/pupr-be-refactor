package com.pupr.persistence.kwitansi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataKwitansiRepository extends JpaRepository<KwitansiJpaEntity, Long> {

    @Query(value = """
            SELECT * FROM kwitansi_transaction
            WHERE bap_no = :bapNo
              AND is_deleted IS DISTINCT FROM TRUE
            LIMIT 1
            """, nativeQuery = true)
    Optional<KwitansiJpaEntity> findActiveByBapNo(@Param("bapNo") String bapNo);

    @Query(value = """
            SELECT * FROM kwitansi_transaction
            WHERE bap_no ~* :pattern
              AND is_deleted IS DISTINCT FROM TRUE
            """, nativeQuery = true)
    List<KwitansiJpaEntity> findActiveByBapNoPattern(@Param("pattern") String pattern);

    @Query(value = """
            SELECT * FROM kwitansi_transaction
            WHERE is_deleted IS DISTINCT FROM TRUE
              AND created_date >= CAST(:startDate AS date)
              AND created_date < CAST(:endDate AS date)
            ORDER BY created_time ASC
            LIMIT :rowLimit
            """, nativeQuery = true)
    List<KwitansiJpaEntity> findActiveByCreatedDateBetween(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("rowLimit") int rowLimit);
}
