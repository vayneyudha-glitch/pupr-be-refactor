package com.pupr.domain.bap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BapRepository {

    Bap save(Bap bap);

    Optional<Bap> findById(Long id);

    Optional<Bap> findByBapNo(String bapNo);

    Optional<Bap> findByBapNoAndCreatedDateAfter(String bapNo, LocalDate createdAfter);

    List<Bap> findByBapNoPatternAndCreatedDateAfter(String pattern, LocalDate createdAfter);

    List<Bap> findByBapDateBetween(String startDate, String endDate);

    List<Bap> findByBapNoPattern(String pattern, int limit);

    List<Bap> findByMasterTransactionId(Long masterTransactionId);

    Optional<Bap> findOneByPaymentNo(String paymentNo);

    void deleteById(Long id);
}
