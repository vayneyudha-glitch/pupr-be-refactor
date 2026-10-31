package com.pupr.application.master;

import com.pupr.domain.master.Master;
import com.pupr.domain.master.MasterNotFoundException;
import com.pupr.domain.master.MasterRepository;
import com.pupr.domain.pekerjaan.Pekerjaan;
import com.pupr.domain.pekerjaan.PekerjaanSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MasterUseCase {

    private static final int TAHUN_ANGGARAN = 2026;

    private final MasterRepository masterRepository;
    private final PekerjaanSource pekerjaanSource;
    private final MasterMapper mapper;

    public MasterUseCase(
            MasterRepository masterRepository,
            PekerjaanSource pekerjaanSource,
            MasterMapper mapper) {
        this.masterRepository = masterRepository;
        this.pekerjaanSource = pekerjaanSource;
        this.mapper = mapper;
    }

    public MasterDto submit(MasterDto request) {
        Master payload = mapper.toDomain(request);
        if (payload.getId() == null) {
            return mapper.toDto(masterRepository.save(payload));
        }

        Master master = masterRepository.findById(payload.getId())
                .orElseThrow(() -> new MasterNotFoundException("Data Gagal Input"));
        if (payload.getNilaiDpa() != null) {
            master.setNilaiDpa(payload.getNilaiDpa());
        }
        if (payload.getSpkNo() != null) {
            master.setSpkNo(payload.getSpkNo());
        }
        if (payload.getNilaiSpk() != null) {
            master.setNilaiSpk(payload.getNilaiSpk());
        }
        if (payload.getTglSpk() != null) {
            master.setTglSpk(payload.getTglSpk());
        }
        // TODO(parity): The legacy submit path does not update mitraNoRekening.
        return mapper.toDto(masterRepository.save(master));
    }

    public List<MasterDto> submitAll(List<MasterDto> payload) {
        List<Master> masters = payload.stream().map(mapper::toDomain).collect(Collectors.toList());
        return masterRepository.saveAll(masters).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public MasterDto getById(String id) {
        return mapper.toDto(masterRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new MasterNotFoundException("No value present")));
    }

    public MasterDto submitSpkMaster(MasterDto request) {
        Master payload = mapper.toDomain(request);
        if (payload.getId() == null) {
            return mapper.toDto(masterRepository.save(payload));
        }

        Master master = masterRepository.findById(payload.getId())
                .orElseThrow(() -> new MasterNotFoundException("Data Gagal Input"));
        if (payload.getSpkNo() != null) {
            master.setSpkNo(payload.getSpkNo());
        }
        if (payload.getNilaiSpk() != null) {
            master.setNilaiSpk(payload.getNilaiSpk());
        }
        if (payload.getTglSpk() != null) {
            master.setTglSpk(payload.getTglSpk());
        }
        if (payload.getTglSpkForm() != null) {
            master.setTglSpkForm(payload.getTglSpkForm());
        }
        if (payload.getTglStartPekerjaan() != null) {
            master.setTglStartPekerjaan(payload.getTglStartPekerjaan());
        }
        if (payload.getTglEndPekerjaan() != null) {
            master.setTglEndPekerjaan(payload.getTglEndPekerjaan());
        }
        master.setMitraId(payload.getMitraId() != null ? payload.getMitraId() : master.getMitraId());
        master.setMitraName(payload.getMitraName() != null ? payload.getMitraName() : master.getMitraName());
        master.setMitraPimpinan(
                payload.getMitraPimpinan() != null ? payload.getMitraPimpinan() : master.getMitraPimpinan());
        master.setMitraNoRekening(
                payload.getMitraNoRekening() != null ? payload.getMitraNoRekening() : master.getMitraNoRekening());
        return mapper.toDto(masterRepository.save(master));
    }

    public MasterDto removeById(String id) {
        Optional<Master> result = masterRepository.findById(Long.valueOf(id));
        Master master = result.orElse(null);
        if (master != null) {
            master.setIsDeleted(true);
            // TODO(parity): The legacy endpoint returns the soft-delete flag without persisting it.
        }
        return mapper.toDto(master);
    }

    public List<MasterDto> getAllByTahunAnggaran(Map<String, String> params) {
        return masterRepository.findAllByYear(params.get("tahun")).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MasterDto> getBySpkNo(String spkNo) {
        return masterRepository.findBySpkNo(spkNo).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<MasterDto> getMasterTrxList(Map<String, String> params) {
        List<Master> masterList = new ArrayList<>();
        String bidang = params.get("bidang");
        String pekerjaan = params.get("pekerjaan");
        String noTrx = params.get("noTrx");

        if (bidang != null) {
            syncPekerjaanByBidang(bidang);
        }

        if (noTrx == null) {
            masterList = masterRepository.findAllByYear(String.valueOf(TAHUN_ANGGARAN));
            // TODO(parity): The legacy filter throws when a stored tahun is null.
            masterList = masterList.stream()
                    .filter(master -> master.getTahun().equals(TAHUN_ANGGARAN))
                    .collect(Collectors.toList());
            if (bidang != null) {
                masterList = masterList.stream()
                        .filter(master -> master.getBidang().equalsIgnoreCase(bidang))
                        .collect(Collectors.toList());
            }
            if (pekerjaan != null) {
                masterList = masterList.stream()
                        .filter(master -> master.getPekerjaan().toLowerCase().contains(pekerjaan.toLowerCase()))
                        .collect(Collectors.toList());
            }
        } else {
            masterList = masterRepository.findByNo(Integer.valueOf(noTrx));
        }
        return masterList.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<MasterDto> syncPekerjaanByBidang(String bidangCode) {
        return syncPekerjaanByBidang(bidangCode, false);
    }

    public List<MasterDto> syncPekerjaanByBidangPatch(String bidangCode) {
        try {
            return syncPekerjaanByBidang(bidangCode, true);
        } catch (RuntimeException exception) {
            throw new IllegalStateException(
                    "Failed to sync & patch pekerjaan for bidangCode: "
                            + bidangCode + " | Reason: " + exception.getMessage(),
                    exception);
        }
    }

    private List<MasterDto> syncPekerjaanByBidang(String bidangCode, boolean patchExisting) {
        List<Pekerjaan> pekerjaanList = pekerjaanSource.findByBidangCode(bidangCode);
        if (pekerjaanList.isEmpty()) {
            throw new IllegalStateException("No pekerjaan data found for bidangCode: " + bidangCode);
        }

        List<MasterDto> added = new ArrayList<>();
        for (Pekerjaan pekerjaan : pekerjaanList) {
            Master master = mapper.fromPekerjaan(pekerjaan);
            List<Master> existing = masterRepository.findByBidangAndPekerjaanAndTahun(
                    master.getBidang(), master.getPekerjaan(),
                    master.getTahun() != null ? master.getTahun() : TAHUN_ANGGARAN);
            if (patchExisting && !existing.isEmpty()) {
                Master current = existing.get(0);
                master.setId(current.getId());
                master.setTahun(current.getTahun() != null ? current.getTahun() : TAHUN_ANGGARAN);
                master.setSpkNo(current.getSpkNo());
                master.setTglSpk(current.getTglSpk());
                // TODO(parity): Legacy PATCH copies mitraName into mitraId.
                master.setMitraId(current.getMitraName());
                master.setMitraName(current.getMitraName());
                master.setMitraPimpinan(current.getMitraPimpinan());
                master.setMitraNoRekening(current.getMitraNoRekening());
                master.setTglStartPekerjaan(current.getTglStartPekerjaan());
                master.setTglEndPekerjaan(current.getTglEndPekerjaan());
                added.add(mapper.toDto(masterRepository.save(master)));
            } else if (!patchExisting && existing.isEmpty()) {
                master.setTahun(TAHUN_ANGGARAN);
                added.add(mapper.toDto(masterRepository.save(master)));
            }
        }
        return added;
    }
}
