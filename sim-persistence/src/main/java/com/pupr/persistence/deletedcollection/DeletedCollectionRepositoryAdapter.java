package com.pupr.persistence.deletedcollection;

import com.pupr.domain.deletedcollection.DeletedCollection;
import com.pupr.domain.deletedcollection.DeletedCollectionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DeletedCollectionRepositoryAdapter implements DeletedCollectionRepository {

    private final SpringDataDeletedCollectionRepository repository;

    public DeletedCollectionRepositoryAdapter(SpringDataDeletedCollectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeletedCollection save(DeletedCollection deletedCollection) {
        return repository.save(DeletedCollectionJpaEntity.fromDomain(deletedCollection)).toDomain();
    }
}
