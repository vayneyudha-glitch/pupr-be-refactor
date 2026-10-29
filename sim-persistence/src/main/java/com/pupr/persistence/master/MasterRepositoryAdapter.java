package com.pupr.persistence.master;

import com.pupr.domain.master.Master;
import com.pupr.domain.master.MasterRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class MasterRepositoryAdapter implements MasterRepository {

    private final SpringDataMasterRepository repository;

    public MasterRepositoryAdapter(SpringDataMasterRepository repository) {
        this.repository = repository;
    }

    @Override
    public Master save(Master master) {
        return repository.save(MasterJpaEntity.fromDomain(master)).toDomain();
    }

    @Override
    public List<Master> saveAll(List<Master> masters) {
        List<MasterJpaEntity> entities = masters.stream()
                .map(MasterJpaEntity::fromDomain)
                .collect(Collectors.toList());
        return repository.saveAll(entities).stream()
                .map(MasterJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Master> findById(Long id) {
        return repository.findById(id).map(MasterJpaEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Master> findAllByYear(String year) {
        // TODO(parity): The legacy DAO ignores year and returns every master transaction.
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(MasterJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Master> findBySpkNo(String spkNoPattern) {
        return repository.findBySpkNoPattern(spkNoPattern).stream()
                .map(MasterJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Master> findByNo(Integer no) {
        return repository.findByNoOrderByIdAsc(no).stream()
                .map(MasterJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Master> findByBidangAndPekerjaanAndTahun(String bidang, String pekerjaan, int tahun) {
        return repository.findByBidangAndPekerjaanAndTahunOrderByIdAsc(bidang, pekerjaan, tahun).stream()
                .map(MasterJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
}
