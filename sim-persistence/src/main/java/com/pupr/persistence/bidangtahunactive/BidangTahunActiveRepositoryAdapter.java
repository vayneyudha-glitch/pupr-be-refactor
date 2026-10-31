package com.pupr.persistence.bidangtahunactive;

import com.pupr.domain.bidangtahunactive.BidangTahunActive;
import com.pupr.domain.bidangtahunactive.BidangTahunActiveRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class BidangTahunActiveRepositoryAdapter implements BidangTahunActiveRepository {

    private final SpringDataBidangTahunActiveRepository repository;

    public BidangTahunActiveRepositoryAdapter(SpringDataBidangTahunActiveRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<BidangTahunActive> findByBidangName(String bidangName) {
        return repository.findByBidangDesc(bidangName).map(BidangTahunActiveJpaEntity::toDomain);
    }
}
