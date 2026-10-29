package com.pupr;

import com.pupr.application.master.MasterMapper;
import com.pupr.application.master.MasterUseCase;
import com.pupr.domain.master.Master;
import com.pupr.domain.master.MasterRepository;
import com.pupr.domain.pekerjaan.Pekerjaan;
import com.pupr.domain.pekerjaan.PekerjaanSource;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MasterUseCaseTest {

    @Test
    void getAllByTahunAnggaranRetainsLegacyUnfilteredResults() {
        MasterRepository repository = mock(MasterRepository.class);
        when(repository.findAllByYear("2024")).thenReturn(List.of(
                Master.builder().id(1L).tahun(2023).build()));
        MasterUseCase useCase = new MasterUseCase(
                repository, mock(PekerjaanSource.class), MasterMapper.INSTANCE);

        var result = useCase.getAllByTahunAnggaran(Map.of("tahun", "2024"));

        assertEquals(1, result.size());
        assertEquals(2023, result.getFirst().tahun());
        verify(repository).findAllByYear("2024");
    }

    @Test
    void removeByIdReturnsSoftDeleteFlagWithoutPersistingIt() {
        MasterRepository repository = mock(MasterRepository.class);
        when(repository.findById(9L)).thenReturn(Optional.of(Master.builder().id(9L).build()));
        MasterUseCase useCase = new MasterUseCase(
                repository, mock(PekerjaanSource.class), MasterMapper.INSTANCE);

        var result = useCase.removeById("9");

        assertTrue(result.isDeleted());
        verify(repository, never()).save(any());
    }

    @Test
    void syncPekerjaanByBidangAddsMissingRecords() {
        MasterRepository repository = mock(MasterRepository.class);
        PekerjaanSource source = mock(PekerjaanSource.class);
        when(source.findByBidangCode("02")).thenReturn(List.of(pekerjaan("02", "Road")));
        when(repository.findByBidangAndPekerjaanAndTahun("02", "Road", 2026)).thenReturn(List.of());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        MasterUseCase useCase = new MasterUseCase(repository, source, MasterMapper.INSTANCE);

        var result = useCase.syncPekerjaanByBidang("02");

        assertEquals(1, result.size());
        verify(repository).save(any());
    }

    @Test
    void syncPekerjaanByBidangPatchUpdatesExistingAndRetainsLegacyMitraIdMapping() {
        MasterRepository repository = mock(MasterRepository.class);
        PekerjaanSource source = mock(PekerjaanSource.class);
        Master existing = Master.builder()
                .id(9L)
                .tahun(2026)
                .spkNo("SPK-1")
                .tglSpk("12-01-2025")
                .mitraId("actual-mitra-id")
                .mitraName("Mitra lama")
                .mitraPimpinan("Pimpinan")
                .mitraNoRekening("rekening")
                .tglStartPekerjaan("start")
                .tglEndPekerjaan("end")
                .build();
        when(source.findByBidangCode("02")).thenReturn(List.of(pekerjaan("02", "Road baru")));
        when(repository.findByBidangAndPekerjaanAndTahun("02", "Road baru", 2026))
                .thenReturn(List.of(existing));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        MasterUseCase useCase = new MasterUseCase(repository, source, MasterMapper.INSTANCE);

        var result = useCase.syncPekerjaanByBidangPatch("02");

        assertEquals(1, result.size());
        assertEquals("9", result.getFirst().id());
        assertEquals(2026, result.getFirst().tahun());
        assertEquals("Road baru", result.getFirst().pekerjaan());
        assertEquals("SPK-1", result.getFirst().spkNo());
        assertEquals("12-01-2025", result.getFirst().tglSpk());
        assertEquals("Mitra lama", result.getFirst().mitraId());
        assertEquals("Mitra lama", result.getFirst().mitraName());
        assertEquals("Pimpinan", result.getFirst().mitraPimpinan());
        assertEquals("rekening", result.getFirst().mitraNoRekening());
        assertEquals("start", result.getFirst().tglStartPekerjaan());
        assertEquals("end", result.getFirst().tglEndPekerjaan());
        verify(repository).save(any());
    }

    private static Pekerjaan pekerjaan(String bidang, String description) {
        return new Pekerjaan(
                bidang, "Anggaran", "Category", "Program", "Program desc",
                "Kegiatan", "Kegiatan desc", "Sub kegiatan", "Sub kegiatan desc",
                "Belanja", "Belanja desc", description, "DPA-1", null,
                "123.45");
    }
}
