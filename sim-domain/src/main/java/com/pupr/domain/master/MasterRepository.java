package com.pupr.domain.master;

import java.util.List;
import java.util.Optional;

public interface MasterRepository {

    Master save(Master master);

    List<Master> saveAll(List<Master> masters);

    Optional<Master> findById(Long id);

    List<Master> findAllByYear(String year);

    List<Master> findBySpkNo(String spkNoPattern);

    List<Master> findByNo(Integer no);

    List<Master> findByBidangAndPekerjaanAndTahun(String bidang, String pekerjaan, int tahun);
}
