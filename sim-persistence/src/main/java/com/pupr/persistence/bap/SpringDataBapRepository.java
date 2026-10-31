package com.pupr.persistence.bap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataBapRepository extends JpaRepository<BapJpaEntity, Long> {

    @Query("""
            SELECT b FROM BapJpaEntity b
            WHERE b.bapNo = :bapNo AND (b.isDeleted IS NULL OR b.isDeleted = false)
            """)
    Optional<BapJpaEntity> findActiveByBapNo(@Param("bapNo") String bapNo);

    @Query("""
            SELECT b FROM BapJpaEntity b
            WHERE b.bapNo = :bapNo
              AND b.createdDate > :createdAfter
              AND (b.isDeleted IS NULL OR b.isDeleted = false)
            """)
    Optional<BapJpaEntity> findActiveByBapNoAndCreatedDateAfter(
            @Param("bapNo") String bapNo,
            @Param("createdAfter") LocalDate createdAfter);

    @Query(value = """
            SELECT * FROM bap_transaction
            WHERE bap_no ~* :pattern
              AND created_date > :createdAfter
              AND is_deleted IS DISTINCT FROM TRUE
            """, nativeQuery = true)
    List<BapJpaEntity> findActiveByBapNoPatternAndCreatedDateAfter(
            @Param("pattern") String pattern,
            @Param("createdAfter") LocalDate createdAfter);

    @Query(value = """
            SELECT * FROM bap_transaction
            WHERE is_deleted IS DISTINCT FROM TRUE
              AND TO_CHAR(bap_date_text, 'YYYY-MM-DD') BETWEEN :startDate AND :endDate
            """, nativeQuery = true)
    List<BapJpaEntity> findActiveByBapDateBetween(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);

    @Query(value = """
            SELECT * FROM bap_transaction
            WHERE bap_no ~* :pattern
              AND is_deleted IS DISTINCT FROM TRUE
            ORDER BY created_time ASC
            LIMIT :rowLimit
            """, nativeQuery = true)
    List<BapJpaEntity> findActiveByBapNoPattern(
            @Param("pattern") String pattern,
            @Param("rowLimit") int rowLimit);

    @Query(value = """
            SELECT * FROM bap_transaction
            WHERE master_transaction_id = :masterTransactionId
              AND is_deleted IS DISTINCT FROM TRUE
            """, nativeQuery = true)
    List<BapJpaEntity> findActiveByMasterTransactionId(
            @Param("masterTransactionId") Long masterTransactionId);

    @Query(value = "SELECT * FROM bap_transaction WHERE payment_no = :paymentNo LIMIT 1", nativeQuery = true)
    Optional<BapJpaEntity> findOneByPaymentNo(@Param("paymentNo") String paymentNo);
}
