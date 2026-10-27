package com.pupr.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataMasterRepository extends JpaRepository<MasterJpaEntity, Long> {

    @Query(value = "SELECT * FROM master_transaction WHERE spk_no ~* :spkNo ORDER BY id", nativeQuery = true)
    List<MasterJpaEntity> findBySpkNoPattern(@Param("spkNo") String spkNo);

    List<MasterJpaEntity> findByNoOrderByIdAsc(Integer no);

    List<MasterJpaEntity> findByBidangAndPekerjaanAndTahunOrderByIdAsc(
            String bidang, String pekerjaan, Integer tahun);
}
