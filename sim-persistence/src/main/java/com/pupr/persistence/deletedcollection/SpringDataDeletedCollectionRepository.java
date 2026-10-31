package com.pupr.persistence.deletedcollection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDeletedCollectionRepository
        extends JpaRepository<DeletedCollectionJpaEntity, Long> {
}
