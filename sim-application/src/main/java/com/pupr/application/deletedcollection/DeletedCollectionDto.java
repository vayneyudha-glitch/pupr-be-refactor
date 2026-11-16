package com.pupr.application.deletedcollection;

import java.time.LocalDateTime;

public record DeletedCollectionDto(
        String id,
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
}
