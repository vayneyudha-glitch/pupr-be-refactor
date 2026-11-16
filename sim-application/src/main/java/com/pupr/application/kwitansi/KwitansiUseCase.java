package com.pupr.application.kwitansi;

import com.pupr.application.bap.BapDto;
import com.pupr.application.bap.BapMapper;
import com.pupr.application.deletedcollection.DeletedCollectionDto;
import com.pupr.domain.bap.Bap;
import com.pupr.domain.bap.BapRepository;
import com.pupr.domain.bidangtahunactive.BidangTahunActiveRepository;
import com.pupr.domain.deletedcollection.DeletedCollection;
import com.pupr.domain.deletedcollection.DeletedCollectionRepository;
import com.pupr.domain.kwitansi.Kwitansi;
import com.pupr.domain.kwitansi.KwitansiDuplicateException;
import com.pupr.domain.kwitansi.KwitansiRepository;
import com.pupr.domain.kwitansi.KwitansiYearValidationException;
import com.pupr.domain.master.Master;
import com.pupr.domain.master.MasterRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KwitansiUseCase {

    // TODO(parity): The legacy year cutoff is fixed to 2026.
    private static final LocalDate LEGACY_YEAR_START = LocalDate.of(2026, 1, 1);
    private static final String BLOCKED_MESSAGE = "Transaction Blocked: Cannot modify data for year ";
    private static final Logger LOGGER = Logger.getLogger(KwitansiUseCase.class.getName());

    private final KwitansiRepository kwitansiRepository;
    private final BapRepository bapRepository;
    private final MasterRepository masterRepository;
    private final BidangTahunActiveRepository bidangTahunActiveRepository;
    private final DeletedCollectionRepository deletedCollectionRepository;

    public KwitansiUseCase(
            KwitansiRepository kwitansiRepository,
            BapRepository bapRepository,
            MasterRepository masterRepository,
            BidangTahunActiveRepository bidangTahunActiveRepository,
            DeletedCollectionRepository deletedCollectionRepository) {
        this.kwitansiRepository = kwitansiRepository;
        this.bapRepository = bapRepository;
        this.masterRepository = masterRepository;
        this.bidangTahunActiveRepository = bidangTahunActiveRepository;
        this.deletedCollectionRepository = deletedCollectionRepository;
    }

    public KwitansiResponseDto submit(KwitansiDto request) {
        Kwitansi kwitansi = KwitansiMapper.toDomain(request);
        validateActiveYear(kwitansi.getMasterTransactionId());

        boolean isNew = kwitansi.getId() == null;
        if (isNew) {
            kwitansi.setCreatedDate(LocalDate.now().toString());
            kwitansi.setCreatedTime(LocalDateTime.now());
            if (kwitansiRepository.findByBapNumber(kwitansi.getBapNo()).isPresent()) {
                throw new KwitansiDuplicateException("Data Duplicated");
            }
        } else {
            Kwitansi existing = kwitansiRepository.findById(kwitansi.getId()).orElseThrow();
            kwitansi.setCreatedDate(existing.getCreatedDate());
        }

        kwitansi.setLastUpdate(LocalDate.now().toString());
        kwitansi.setLastUpdateTime(LocalDateTime.now());
        kwitansi.setIsDeleted(false);

        Kwitansi saved = kwitansiRepository.save(kwitansi);
        if (isNew) {
            linkKwitansiToBap(saved);
        }
        return new KwitansiResponseDto(null, null, KwitansiMapper.toDto(saved), null);
    }

    public List<KwitansiDto> findByBapNo(String bapNo) {
        return kwitansiRepository.findByBapNoPattern(bapNo).stream()
                .map(KwitansiMapper::toDto)
                .toList();
    }

    public List<KwitansiDto> findByLimit1(Map<String, String> params) {
        return kwitansiRepository.findByCreatedDateBetween(
                        params.get("startDate"), params.get("endDate"), 1000)
                .stream()
                .map(KwitansiMapper::toDto)
                .toList();
    }

    public List<BapDto> findByLimit1V2(Map<String, String> params) {
        String bapNo = params.get("bapNo");
        String bidang = params.get("bidang");
        List<Bap> baps;
        if (bapNo != null) {
            baps = bapRepository.findByBapNoPatternAndCreatedDateAfter(bapNo, LEGACY_YEAR_START);
        } else {
            baps = bapRepository.findByBapDateBetween(params.get("startDate"), params.get("endDate"));
            if (bidang != null) {
                baps = baps.stream()
                        .filter(bap -> hydrateMaster(bap).getMaster().getBidang().equalsIgnoreCase(bidang))
                        .toList();
            }
            baps = baps.stream().filter(bap -> bap.getKwitansi() != null).toList();
        }
        return baps.stream()
                .map(this::hydrateMaster)
                .map(BapMapper::toDto)
                .toList();
    }

    public DeletedCollectionDto removeById(Map<String, String> params) {
        Bap bap = bapRepository.findById(parseId(params.get("id"))).orElseThrow();

        Map<String, Object> kwitansiReference = bap.getKwitansi();
        String referenceId = kwitansiReference == null
                ? null
                : String.valueOf(kwitansiReference.get("id"));
        Kwitansi kwitansi = referenceId == null || "null".equals(referenceId)
                ? null
                : kwitansiRepository.findById(parseId(referenceId)).orElse(null);
        LocalDateTime deletedDate = LocalDateTime.now();
        DeletedCollection audit = new DeletedCollection(
                null,
                null,
                bap.getBapNo(),
                "KWITANSI",
                legacyKwitansiString(kwitansi),
                null,
                null,
                null,
                null,
                null,
                null,
                params.get("deletedBy"),
                null,
                deletedDate.toString(),
                LocalDateTime.now());

        DeletedCollection savedAudit = deletedCollectionRepository.save(audit);
        // TODO(parity): Legacy dereferences a missing BAP Kwitansi DBRef after saving its audit row.
        kwitansiRepository.deleteById(kwitansi.getId());
        // TODO(parity): Clearing JSONB emulates Mongo DBRef resolution after the referenced document is deleted.
        bap.setKwitansi(null);
        bapRepository.save(bap);
        return new DeletedCollectionDto(
                stringId(savedAudit.id()),
                savedAudit.idTrx(),
                savedAudit.noTrx(),
                savedAudit.typeTrx(),
                savedAudit.valueTrx(),
                savedAudit.createdBy(),
                savedAudit.createdDate(),
                savedAudit.createdTime(),
                savedAudit.updateBy(),
                savedAudit.lastUpdate(),
                savedAudit.lastUpdateTime(),
                savedAudit.deletedBy(),
                savedAudit.isDeleted(),
                deletedDate.toString(),
                savedAudit.deletedTime());
    }

    private void validateActiveYear(Long masterId) {
        boolean valid = false;
        if (masterId != null) {
            Optional<Master> master = masterRepository.findById(masterId);
            if (master.isPresent()) {
                Optional<com.pupr.domain.bidangtahunactive.BidangTahunActive> activeYear =
                        bidangTahunActiveRepository.findByBidangName(master.get().getBidang());
                if (activeYear.isPresent()) {
                    String activeYearValue = activeYear.get().tahunActive();
                    if (activeYearValue != null) {
                        // TODO(parity): Legacy throws NullPointerException when Master.tahun is null.
                        valid = activeYearValue.equalsIgnoreCase(master.get().getTahun().toString());
                    }
                }
            }
        }
        if (!valid) {
            throw new KwitansiYearValidationException(BLOCKED_MESSAGE);
        }
    }

    private void linkKwitansiToBap(Kwitansi kwitansi) {
        try {
            Bap bap = bapRepository.findById(parseId(kwitansi.getBapId())).orElseThrow();
            bap.setKwitansi(Map.of("id", kwitansi.getId().toString()));
            bapRepository.save(bap);
        } catch (RuntimeException exception) {
            // TODO(parity): Legacy BapServiceImpl swallows child-link failures and still saves Kwitansi.
            LOGGER.log(Level.WARNING, "Unable to update the BAP Kwitansi reference", exception);
        }
    }

    private Bap hydrateMaster(Bap bap) {
        if (bap.getMasterTransactionId() != null) {
            Master master = masterRepository.findById(bap.getMasterTransactionId()).orElse(null);
            bap.setMaster(master);
        }
        return bap;
    }

    private static String legacyKwitansiString(Kwitansi kwitansi) {
        if (kwitansi == null) {
            return "null";
        }
        return "Kwitansi(id=" + kwitansi.getId()
                + ", bapId=" + kwitansi.getBapId()
                + ", masterTransactionId=" + kwitansi.getMasterTransactionId()
                + ", bapNo=" + kwitansi.getBapNo()
                + ", description=" + kwitansi.getDescription()
                + ", kepalaBidangNo=" + kwitansi.getKepalaBidangNo()
                + ", kepalaBidangName=" + kwitansi.getKepalaBidangName()
                + ", ppkNo=" + kwitansi.getPpkNo()
                + ", ppkName=" + kwitansi.getPpkName()
                + ", pptkNo=" + kwitansi.getPptkNo()
                + ", pptkName=" + kwitansi.getPptkName()
                + ", bppNo=" + kwitansi.getBppNo()
                + ", bppName=" + kwitansi.getBppName()
                + ", direkturNo=" + kwitansi.getDirekturNo()
                + ", direkturName=" + kwitansi.getDirekturName()
                + ", kwitansiDate=" + kwitansi.getKwitansiDate() + ")";
    }

    private static Long parseId(String id) {
        return id == null || id.isBlank() ? null : Long.valueOf(id);
    }

    private static String stringId(Long id) {
        return id == null ? null : id.toString();
    }
}
