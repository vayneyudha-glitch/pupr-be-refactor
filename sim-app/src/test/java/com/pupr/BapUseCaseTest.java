package com.pupr;

import com.pupr.application.bap.BapDto;
import com.pupr.application.bap.BapUseCase;
import com.pupr.domain.bap.Bap;
import com.pupr.domain.bap.BapRepository;
import com.pupr.domain.bap.BapYearValidationException;
import com.pupr.domain.bidangtahunactive.BidangTahunActive;
import com.pupr.domain.bidangtahunactive.BidangTahunActiveRepository;
import com.pupr.domain.deletedcollection.DeletedCollectionRepository;
import com.pupr.domain.master.Master;
import com.pupr.domain.master.MasterRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BapUseCaseTest {

    @Test
    void submitIsForbiddenWhenNoActiveYearExists() {
        BapRepository bapRepository = mock(BapRepository.class);
        MasterRepository masterRepository = mock(MasterRepository.class);
        BidangTahunActiveRepository activeYearRepository = mock(BidangTahunActiveRepository.class);
        when(masterRepository.findById(7L))
                .thenReturn(Optional.of(Master.builder().id(7L).bidang("Bidang A").tahun(2026).build()));
        when(activeYearRepository.findByBidangName("Bidang A")).thenReturn(Optional.empty());
        BapUseCase useCase = useCase(bapRepository, masterRepository, activeYearRepository);

        assertThrows(BapYearValidationException.class, () -> useCase.submit(request("7")));
        verify(bapRepository, never()).save(any());
    }

    @Test
    void submitIsForbiddenWhenActiveYearDoesNotMatchMasterYear() {
        BapRepository bapRepository = mock(BapRepository.class);
        MasterRepository masterRepository = mock(MasterRepository.class);
        BidangTahunActiveRepository activeYearRepository = mock(BidangTahunActiveRepository.class);
        when(masterRepository.findById(7L))
                .thenReturn(Optional.of(Master.builder().id(7L).bidang("Bidang A").tahun(2025).build()));
        when(activeYearRepository.findByBidangName("Bidang A"))
                .thenReturn(Optional.of(new BidangTahunActive(1L, "A", "Bidang A", "2026")));
        BapUseCase useCase = useCase(bapRepository, masterRepository, activeYearRepository);

        assertThrows(BapYearValidationException.class, () -> useCase.submit(request("7")));
        verify(bapRepository, never()).save(any());
    }

    @Test
    void submitMapsStringMasterIdAndUsesDatabaseIdentity() {
        BapRepository bapRepository = mock(BapRepository.class);
        MasterRepository masterRepository = mock(MasterRepository.class);
        BidangTahunActiveRepository activeYearRepository = mock(BidangTahunActiveRepository.class);
        when(masterRepository.findById(7L))
                .thenReturn(Optional.of(Master.builder().id(7L).bidang("Bidang A").tahun(2026).build()));
        when(activeYearRepository.findByBidangName("Bidang A"))
                .thenReturn(Optional.of(new BidangTahunActive(1L, "A", "Bidang A", "2026")));
        when(bapRepository.findByBapNo("BAP-1")).thenReturn(Optional.empty());
        when(bapRepository.save(any())).thenAnswer(invocation -> {
            Bap saved = invocation.getArgument(0);
            saved.setId(42L);
            return saved;
        });
        BapUseCase useCase = useCase(bapRepository, masterRepository, activeYearRepository);

        BapDto result = useCase.submit(request("7"));

        assertEquals("42", result.id());
        assertEquals("7", result.masterTransactionId());
        assertEquals("BAP-1", result.bapNo());
        assertNotNull(result.createdTime());
        assertNotNull(result.hibahDate());
        verify(activeYearRepository).findByBidangName("Bidang A");
    }

    @Test
    void noMasterTransactionMatchesReturnLegacyEmptyObjectForSingleResultRoute() {
        BapRepository bapRepository = mock(BapRepository.class);
        when(bapRepository.findByMasterTransactionId(9L)).thenReturn(List.of());
        BapUseCase useCase = useCase(
                bapRepository, mock(MasterRepository.class), mock(BidangTahunActiveRepository.class));

        BapDto result = useCase.findByMasterTrxId("9");

        assertNotNull(result);
        assertEquals(null, result.id());
    }

    private static BapUseCase useCase(
            BapRepository bapRepository,
            MasterRepository masterRepository,
            BidangTahunActiveRepository activeYearRepository) {
        return new BapUseCase(
                bapRepository,
                masterRepository,
                activeYearRepository,
                mock(DeletedCollectionRepository.class));
    }

    private static BapDto request(String masterTransactionId) {
        Date bapDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return new BapDto(
                null, masterTransactionId, "BAP-1", bapDate, null, null, null,
                null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null);
    }
}
