package com.pupr.domain.deletedcollection;

import java.time.LocalDateTime;

public record DeletedCollection(
        Long id,
        String idTrx,
        String noTrx,
        String typeTrx,
        String valueTrx,
        String createdBy,
        String createdDate,
        LocalDateTime createdTime,
        String updateBy,
        String lastUpdate,
        LocalDateTime lastUpdateTime,
        String deletedBy,
        Boolean isDeleted,
        String deletedDate,
        LocalDateTime deletedTime) {

    public DeletedCollection withId(Long newId) {
        return new DeletedCollection(
                newId, idTrx, noTrx, typeTrx, valueTrx, createdBy, createdDate, createdTime,
                updateBy, lastUpdate, lastUpdateTime, deletedBy, isDeleted, deletedDate, deletedTime);
    }
}
