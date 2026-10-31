package com.pupr.persistence.deletedcollection;

import com.pupr.domain.deletedcollection.DeletedCollection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "deleted_collection")
public class DeletedCollectionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_trx")
    private String idTrx;
    @Column(name = "no_trx")
    private String noTrx;
    @Column(name = "type_trx")
    private String typeTrx;
    @Column(name = "value_trx")
    private String valueTrx;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "last_update")
    private LocalDate lastUpdate;
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
    @Column(name = "deleted_by")
    private String deletedBy;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "deleted_date")
    private LocalDate deletedDate;
    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    protected DeletedCollectionJpaEntity() {
    }

    public static DeletedCollectionJpaEntity fromDomain(DeletedCollection deletedCollection) {
        DeletedCollectionJpaEntity entity = new DeletedCollectionJpaEntity();
        entity.id = deletedCollection.id();
        entity.idTrx = deletedCollection.idTrx();
        entity.noTrx = deletedCollection.noTrx();
        entity.typeTrx = deletedCollection.typeTrx();
        entity.valueTrx = deletedCollection.valueTrx();
        entity.createdBy = deletedCollection.createdBy();
        entity.createdDate = parseDate(deletedCollection.createdDate());
        entity.createdTime = deletedCollection.createdTime();
        entity.updateBy = deletedCollection.updateBy();
        entity.lastUpdate = parseDate(deletedCollection.lastUpdate());
        entity.lastUpdateTime = deletedCollection.lastUpdateTime();
        entity.deletedBy = deletedCollection.deletedBy();
        entity.isDeleted = deletedCollection.isDeleted();
        entity.deletedDate = parseDate(deletedCollection.deletedDate());
        entity.deletedTime = deletedCollection.deletedTime();
        return entity;
    }

    public DeletedCollection toDomain() {
        return new DeletedCollection(
                id,
                idTrx,
                noTrx,
                typeTrx,
                valueTrx,
                createdBy,
                formatDate(createdDate),
                createdTime,
                updateBy,
                formatDate(lastUpdate),
                lastUpdateTime,
                deletedBy,
                isDeleted,
                formatDate(deletedDate),
                deletedTime);
    }

    private static LocalDate parseDate(String value) {
        return value == null ? null : LocalDate.parse(value.substring(0, 10));
    }

    private static String formatDate(LocalDate value) {
        // TODO(parity): The legacy DeletedCollection.deletedDate may contain a timestamp string.
        return value == null ? null : value.toString();
    }
}
