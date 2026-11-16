package com.pupr.application.bap;

import com.pupr.application.master.MasterMapper;
import com.pupr.domain.bap.Bap;
import com.pupr.domain.bap.Pengajuan;
import com.pupr.domain.master.Master;

public final class BapMapper {

    private BapMapper() {
    }

    public static BapDto toDto(Bap bap) {
        if (bap == null) {
            return null;
        }
        return new BapDto(
                stringId(bap.getId()),
                stringId(bap.getMasterTransactionId()),
                bap.getBapNo(),
                bap.getBapDate(),
                bap.getBapDateText(),
                bap.getBapAmount(),
                bap.getContrackAmount(),
                bap.getPaymentMethodId(),
                bap.getPaymentMethodDesc(),
                bap.getHibahNo(),
                bap.getHibahDate(),
                bap.getNamePengajuan(),
                bap.getPhoneNumberPengajuan(),
                bap.getPengajuan() == null
                        ? null
                        : new PengajuanDto(bap.getPengajuan().name(), bap.getPengajuan().phoneNumber()),
                bap.getValidItemanis(),
                MasterMapper.INSTANCE.toDto(bap.getMaster()),
                bap.getRingkasan(),
                bap.getKwitansi(),
                bap.getSpp(),
                bap.getSp2D(),
                bap.getSpm(),
                bap.getValidasi(),
                bap.getRekom(),
                bap.getCreatedBy(),
                bap.getCreatedDate(),
                bap.getCreatedTime(),
                bap.getUpdateBy(),
                bap.getLastUpdate(),
                bap.getLastUpdateTime(),
                bap.getDeletedBy(),
                bap.getIsDeleted(),
                bap.getDeletedDate(),
                bap.getDeletedTime());
    }

    public static Bap toDomain(BapDto dto) {
        if (dto == null) {
            return null;
        }
        return Bap.builder()
                .id(parseId(dto.id()))
                .masterTransactionId(parseId(dto.masterTransactionId()))
                .bapNo(dto.bapNo())
                .bapDate(dto.bapDate())
                .bapDateText(dto.bapDateText())
                .bapAmount(dto.bapAmount())
                .contrackAmount(dto.contrackAmount())
                .paymentMethodId(dto.paymentMethodId())
                .paymentMethodDesc(dto.paymentMethodDesc())
                .hibahNo(dto.hibahNo())
                .hibahDate(dto.hibahDate())
                .namePengajuan(dto.namePengajuan())
                .phoneNumberPengajuan(dto.phoneNumberPengajuan())
                .pengajuan(dto.pengajuan() == null
                        ? null
                        : new Pengajuan(dto.pengajuan().name(), dto.pengajuan().phoneNumber()))
                .validItemanis(dto.validItemanis())
                .master(MasterMapper.INSTANCE.toDomain(dto.master()))
                .ringkasan(dto.ringkasan())
                .kwitansi(dto.kwitansi())
                .spp(dto.spp())
                .sp2D(dto.sp2D())
                .spm(dto.spm())
                .validasi(dto.validasi())
                .rekom(dto.rekom())
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
