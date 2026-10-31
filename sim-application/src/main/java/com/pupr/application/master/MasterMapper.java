package com.pupr.application.master;

import com.pupr.domain.master.Master;
import com.pupr.domain.pekerjaan.Pekerjaan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface MasterMapper {

    MasterMapper INSTANCE = Mappers.getMapper(MasterMapper.class);

    MasterDto toDto(Master master);

    Master toDomain(MasterDto dto);

    default Master fromPekerjaan(Pekerjaan pekerjaan) {
        return Master.builder()
                .bidang(pekerjaan.bidangCode())
                .anggaran(pekerjaan.anggaranDesc())
                .kategory(pekerjaan.categoryType())
                .kodeProgram(pekerjaan.programCode())
                .programDesc(pekerjaan.programDescription())
                .kodeKegiatan(pekerjaan.kegiatanCodeRef())
                .kegiatanDesc(pekerjaan.kegiatanDescRef())
                .kodeSubKegiatan(pekerjaan.programCode() + "."
                        + pekerjaan.kegiatanCodeRef() + "."
                        + pekerjaan.subKegiatanCodeRef() + ".")
                .subKegitanDesc(pekerjaan.subKegiatanDescRef())
                .kodeBelanja(pekerjaan.belanjaCodeRef())
                .belanjaDesc(pekerjaan.belanjaDescRef())
                .pekerjaan(pekerjaan.description())
                .noDpa(pekerjaan.noDpa())
                .tglDpa(formatDpaDate(pekerjaan.dpaDate()))
                // TODO(parity): The legacy mapper throws when dpaValue is null.
                .nilaiDpa(new BigDecimal(pekerjaan.dpaValue()))
                .build();
    }

    private static String formatDpaDate(Date date) {
        return date == null ? "" : new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}
