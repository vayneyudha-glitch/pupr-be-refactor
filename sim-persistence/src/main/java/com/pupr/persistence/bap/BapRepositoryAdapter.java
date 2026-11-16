package com.pupr.persistence.bap;

import com.pupr.domain.bap.Bap;
import com.pupr.domain.bap.BapRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BapRepositoryAdapter implements BapRepository {

    private final SpringDataBapRepository repository;

    public BapRepositoryAdapter(SpringDataBapRepository repository) {
        this.repository = repository;
    }

    @Override
    public Bap save(Bap bap) {
        return repository.save(BapJpaEntity.fromDomain(bap)).toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bap> findById(Long id) {
        return repository.findById(id).map(BapJpaEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bap> findByBapNo(String bapNo) {
        return repository.findActiveByBapNo(bapNo).map(BapJpaEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bap> findByBapNoAndCreatedDateAfter(String bapNo, LocalDate createdAfter) {
        return repository.findActiveByBapNoAndCreatedDateAfter(bapNo, createdAfter)
                .map(BapJpaEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bap> findByBapNoPatternAndCreatedDateAfter(String pattern, LocalDate createdAfter) {
        return repository.findActiveByBapNoPatternAndCreatedDateAfter(pattern, createdAfter).stream()
                .map(BapJpaEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bap> findByBapDateBetween(String startDate, String endDate) {
        return repository.findActiveByBapDateBetween(startDate, endDate).stream()
                .map(BapJpaEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bap> findByBapNoPattern(String pattern, int limit) {
        return repository.findActiveByBapNoPattern(pattern, limit).stream()
                .map(BapJpaEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bap> findByMasterTransactionId(Long masterTransactionId) {
        return repository.findActiveByMasterTransactionId(masterTransactionId).stream()
                .map(BapJpaEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bap> findOneByPaymentNo(String paymentNo) {
        return repository.findOneByPaymentNo(paymentNo).map(BapJpaEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
