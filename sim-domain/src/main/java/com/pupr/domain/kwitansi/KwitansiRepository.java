package com.pupr.domain.kwitansi;

import java.util.List;
import java.util.Optional;

public interface KwitansiRepository {

    Kwitansi save(Kwitansi kwitansi);

    Optional<Kwitansi> findById(Long id);

    Optional<Kwitansi> findByBapNumber(String bapNo);

    List<Kwitansi> findByBapNoPattern(String pattern);

    List<Kwitansi> findByCreatedDateBetween(String startDate, String endDate, int limit);

    void deleteById(Long id);
}
