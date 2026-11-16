package com.pupr.application.kwitansi;

import com.pupr.domain.kwitansi.Kwitansi;

public final class KwitansiMapper {

    private KwitansiMapper() {
    }

    public static KwitansiDto toDto(Kwitansi kwitansi) {
        if (kwitansi == null) {
            return null;
        }
        return new KwitansiDto(
                stringId(kwitansi.getId()),
                kwitansi.getBapId(),
                stringId(kwitansi.getMasterTransactionId()),
                kwitansi.getBapNo(),
                kwitansi.getDescription(),
                kwitansi.getKepalaBidangNo(),
                kwitansi.getKepalaBidangName(),
                kwitansi.getPpkNo(),
                kwitansi.getPpkName(),
                kwitansi.getPptkNo(),
                kwitansi.getPptkName(),
                kwitansi.getBppNo(),
                kwitansi.getBppName(),
                kwitansi.getDirekturNo(),
                kwitansi.getDirekturName(),
                kwitansi.getKwitansiDate(),
                kwitansi.getCreatedBy(),
                kwitansi.getCreatedDate(),
                kwitansi.getCreatedTime(),
                kwitansi.getUpdateBy(),
                kwitansi.getLastUpdate(),
                kwitansi.getLastUpdateTime(),
                kwitansi.getDeletedBy(),
                kwitansi.getIsDeleted(),
                kwitansi.getDeletedDate(),
                kwitansi.getDeletedTime());
    }

    public static Kwitansi toDomain(KwitansiDto dto) {
        if (dto == null) {
            return null;
        }
        return Kwitansi.builder()
                .id(parseId(dto.id()))
                .bapId(dto.bapId())
                .masterTransactionId(parseId(dto.masterTransactionId()))
                .bapNo(dto.bapNo())
                .description(dto.description())
                .kepalaBidangNo(dto.kepalaBidangNo())
                .kepalaBidangName(dto.kepalaBidangName())
                .ppkNo(dto.ppkNo())
                .ppkName(dto.ppkName())
                .pptkNo(dto.pptkNo())
                .pptkName(dto.pptkName())
                .bppNo(dto.bppNo())
                .bppName(dto.bppName())
                .direkturNo(dto.direkturNo())
                .direkturName(dto.direkturName())
                .kwitansiDate(dto.kwitansiDate())
                .createdBy(dto.createdBy())
                .createdDate(dto.createdDate())
                .createdTime(dto.createdTime())
                .updateBy(dto.updateBy())
                .lastUpdate(dto.lastUpdate())
                .lastUpdateTime(dto.lastUpdateTime())
                .deletedBy(dto.deletedBy())
                .isDeleted(dto.isDeleted())
                .deletedDate(dto.deletedDate())
                .deletedTime(dto.deletedTime())
                .build();
    }

    private static String stringId(Long id) {
        return id == null ? null : id.toString();
    }

    private static Long parseId(String id) {
        return id == null || id.isBlank() ? null : Long.valueOf(id);
    }
}
