package com.pupr.application.bap;

import com.pupr.application.master.MasterMapper;
import com.pupr.domain.bap.Bap;
import com.pupr.domain.bap.BapDuplicateException;
import com.pupr.domain.bap.BapRepository;
import com.pupr.domain.bap.BapYearValidationException;
import com.pupr.domain.bidangtahunactive.BidangTahunActive;
import com.pupr.domain.bidangtahunactive.BidangTahunActiveRepository;
import com.pupr.domain.deletedcollection.DeletedCollection;
import com.pupr.domain.deletedcollection.DeletedCollectionRepository;
import com.pupr.application.deletedcollection.DeletedCollectionDto;
import com.pupr.domain.master.Master;
import com.pupr.domain.master.MasterRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BapUseCase {

    // TODO(parity): Legacy START_FROM_TAHUN_ANGGARAN is fixed to 2026.
    private static final LocalDate LEGACY_YEAR_START = LocalDate.of(2026, 1, 1);
    // TODO(parity): The legacy shared SimpleDateFormat is not thread-safe.
    private static final SimpleDateFormat LEGACY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String BLOCKED_MESSAGE = "Transaction Blocked: Cannot modify data for year ";

    private final BapRepository bapRepository;
    private final MasterRepository masterRepository;
    private final BidangTahunActiveRepository bidangTahunActiveRepository;
    private final DeletedCollectionRepository deletedCollectionRepository;

    public BapUseCase(
            BapRepository bapRepository,
            MasterRepository masterRepository,
            BidangTahunActiveRepository bidangTahunActiveRepository,
            DeletedCollectionRepository deletedCollectionRepository) {
        this.bapRepository = bapRepository;
        this.masterRepository = masterRepository;
        this.bidangTahunActiveRepository = bidangTahunActiveRepository;
        this.deletedCollectionRepository = deletedCollectionRepository;
    }

    public BapDto submit(BapDto request) {
        Bap bap = BapMapper.toDomain(request);
        validateActiveYear(bap.getMasterTransactionId());

        if (bap.getId() == null) {
            bap.setCreatedDate(LocalDate.now().toString());
            bap.setCreatedTime(LocalDateTime.now());
            if (bapRepository.findByBapNo(bap.getBapNo()).isPresent()) {
                throw new BapDuplicateException("Data Duplicated");
            }
            bap.setMaster(Master.builder().id(bap.getMasterTransactionId()).build());
        } else {
            Bap existing = bapRepository.findById(bap.getId()).orElseThrow();
            hydrateMaster(existing);
            bap.setCreatedDate(existing.getCreatedDate());
            bap.setCreatedTime(existing.getCreatedTime());
            bap.setMaster(existing.getMaster());
            bap.setRingkasan(existing.getRingkasan());
            bap.setKwitansi(existing.getKwitansi());
            bap.setSpp(existing.getSpp());
        }

        // TODO(parity): The legacy SimpleDateFormat throws when bapDate is null.
        bap.setBapDateText(LEGACY_DATE_FORMAT.format(bap.getBapDate()));
        bap.setLastUpdate(LocalDate.now().toString());
        bap.setLastUpdateTime(LocalDateTime.now());
        bap.setIsDeleted(false);
        if (bap.getHibahDate() == null) {
            bap.setHibahDate(new Date());
        }
        return BapMapper.toDto(bapRepository.save(bap));
    }

    public BapResponseDto findById(String id) {
        Bap bap = bapRepository.findById(parseId(id)).orElseThrow();
        // TODO(parity): Legacy calls Optional.get() when the referenced Master is absent.
        Master master = masterRepository.findById(bap.getMasterTransactionId()).get();
        bap.setMaster(master);
        return new BapResponseDto(BapMapper.toDto(bap), MasterMapper.INSTANCE.toDto(master),
                null, null, null, null, null, null);
    }

    public BapDto findByMasterTrxId(String masterTrxId) {
        List<Bap> baps = sortNewest(bapRepository.findByMasterTransactionId(parseId(masterTrxId)));
        if (baps.isEmpty()) {
            return BapDto.empty();
        }
        Bap bap = baps.getFirst();
        hydrateMaster(bap);
        return BapMapper.toDto(bap);
    }

    public List<BapDto> findByMasterTrxIdV2(String masterTrxId) {
        List<Bap> baps = sortNewest(bapRepository.findByMasterTransactionId(parseId(masterTrxId)));
        if (baps.isEmpty()) {
            // TODO(parity): The legacy service returns null, not an empty list, for no matches.
            return null;
        }
        return toDtosWithMaster(baps);
    }

    public List<BapDto> findByBapNoV2(String bapNo) {
        return toDtosWithMaster(
                bapRepository.findByBapNoPatternAndCreatedDateAfter(bapNo, LEGACY_YEAR_START));
    }

    public List<BapDto> findByLimit1(Map<String, String> params) {
        String bapNo = params.get("bapNo");
        String bidang = params.get("bidang");
        List<Bap> baps;
        if (bapNo == null) {
            baps = bapRepository.findByBapDateBetween(params.get("startDate"), params.get("endDate"));
        } else {
            baps = bapRepository.findByBapNoPatternAndCreatedDateAfter(bapNo, LEGACY_YEAR_START);
        }
        for (Bap bap : baps) {
            hydrateMaster(bap);
        }
        if (bidang != null) {
            // TODO(parity): Legacy dereferences the Master DBRef and throws if it is missing.
            baps = baps.stream()
                    .filter(bap -> bap.getMaster().getBidang().equalsIgnoreCase(bidang))
                    .toList();
        }
        // TODO(parity): Legacy creates a Pageable, but its DAO query ignores it.
        return baps.stream().map(BapMapper::toDto).toList();
    }

    public List<BapDto> findByBapNoLimit(String bapNo) {
        return toDtosWithMaster(bapRepository.findByBapNoPattern(bapNo, 50));
    }

    public BapDto findOneByHibahNo(String noHibah) {
        // TODO(parity): The legacy repository queries paymentNo instead of hibahNo.
        return BapMapper.toDto(
                bapRepository.findOneByPaymentNo(noHibah).map(this::hydrateMaster).orElse(null));
    }

    public BapDto updateValidItemanis(Map<String, String> params) {
        Bap bap = bapRepository.findById(parseId(params.get("bapId"))).orElseThrow();
        hydrateMaster(bap);
        bap.setValidItemanis(Boolean.valueOf(params.get("validStatus")));
        return BapMapper.toDto(bapRepository.save(bap));
    }

    public DeletedCollectionDto removeById(Map<String, String> params) {
        Bap bap = bapRepository.findById(parseId(params.get("id"))).orElseThrow();
        hydrateMaster(bap);
        LocalDateTime now = LocalDateTime.now();
        DeletedCollection audit = new DeletedCollection(
                null, null, bap.getBapNo(), "BAP", String.valueOf(bap),
                null, null, null, null, null, null, params.get("deletedBy"),
                null, now.toString(), now);
        DeletedCollection savedAudit = deletedCollectionRepository.save(audit);
        bapRepository.deleteById(bap.getId());
        return toDto(savedAudit);
    }

    private void validateActiveYear(Long masterId) {
        if (masterId == null) {
            throw new BapYearValidationException(BLOCKED_MESSAGE);
        }
        Optional<Master> master = masterRepository.findById(masterId);
        boolean valid = false;
        if (master.isPresent()) {
            Optional<BidangTahunActive> activeYear =
                    bidangTahunActiveRepository.findByBidangName(master.get().getBidang());
            if (activeYear.isPresent()) {
                String dbYear = activeYear.get().tahunActive();
                // TODO(parity): Legacy throws NullPointerException when Master.tahun is null.
                if (dbYear != null && dbYear.equalsIgnoreCase(master.get().getTahun().toString())) {
                    valid = true;
                }
            }
        }
        if (!valid) {
            throw new BapYearValidationException(BLOCKED_MESSAGE);
        }
    }

    private List<BapDto> toDtosWithMaster(List<Bap> baps) {
        baps.forEach(this::hydrateMaster);
        return baps.stream().map(BapMapper::toDto).toList();
    }

    private Bap hydrateMaster(Bap bap) {
        bap.setMaster(bap.getMasterTransactionId() == null
                ? null
                : masterRepository.findById(bap.getMasterTransactionId()).orElse(null));
        return bap;
    }

    private static List<Bap> sortNewest(List<Bap> baps) {
        // TODO(parity): Legacy comparator throws if a BAP has no createdTime.
        return baps.stream()
                .sorted(Comparator.comparing(Bap::getCreatedTime).reversed())
                .collect(Collectors.toList());
    }

    private static Long parseId(String id) {
        return id == null || id.isBlank() ? null : Long.valueOf(id);
    }

    private static DeletedCollectionDto toDto(DeletedCollection deletedCollection) {
        return new DeletedCollectionDto(
                deletedCollection.id() == null ? null : deletedCollection.id().toString(),
                deletedCollection.idTrx(),
                deletedCollection.noTrx(),
                deletedCollection.typeTrx(),
                deletedCollection.valueTrx(),
                deletedCollection.createdBy(),
                deletedCollection.createdDate(),
                deletedCollection.createdTime(),
                deletedCollection.updateBy(),
                deletedCollection.lastUpdate(),
                deletedCollection.lastUpdateTime(),
                deletedCollection.deletedBy(),
                deletedCollection.isDeleted(),
                deletedCollection.deletedDate(),
                deletedCollection.deletedTime());
    }
}
