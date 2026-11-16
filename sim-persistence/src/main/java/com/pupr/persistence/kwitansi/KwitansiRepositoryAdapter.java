package com.pupr.persistence.kwitansi;

import com.pupr.domain.kwitansi.Kwitansi;
import com.pupr.domain.kwitansi.KwitansiRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class KwitansiRepositoryAdapter implements KwitansiRepository {

    private final SpringDataKwitansiRepository repository;

    public KwitansiRepositoryAdapter(SpringDataKwitansiRepository repository) {
        this.repository = repository;
    }

    @Override
    public Kwitansi save(Kwitansi kwitansi) {
        return repository.save(KwitansiJpaEntity.fromDomain(kwitansi)).toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Kwitansi> findById(Long id) {
        return repository.findById(id).map(KwitansiJpaEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Kwitansi> findByBapNumber(String bapNo) {
        return repository.findActiveByBapNo(bapNo).map(KwitansiJpaEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Kwitansi> findByBapNoPattern(String pattern) {
        return repository.findActiveByBapNoPattern(pattern).stream()
                .map(KwitansiJpaEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Kwitansi> findByCreatedDateBetween(String startDate, String endDate, int limit) {
        return repository.findActiveByCreatedDateBetween(startDate, endDate, limit).stream()
                .map(KwitansiJpaEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
