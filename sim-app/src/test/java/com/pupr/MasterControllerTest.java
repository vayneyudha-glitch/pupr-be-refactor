package com.pupr;

import com.pupr.application.MasterUseCase;
import com.pupr.web.MasterController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MasterControllerTest {

    private MasterUseCase masterUseCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        masterUseCase = mock(MasterUseCase.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new MasterController(masterUseCase)).build();
    }

    @Test
    void getSyncPekerjaanByBidangUsesLegacyRouteAndQueryParameter() throws Exception {
        when(masterUseCase.syncPekerjaanByBidang("02")).thenReturn(List.of());

        mockMvc.perform(get("/Master/syncPekerjaanByBidang").param("bidangCode", "02"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(masterUseCase).syncPekerjaanByBidang("02");
    }

    @Test
    void patchSyncPekerjaanByBidangUsesLegacyRouteAndRequestBody() throws Exception {
        when(masterUseCase.syncPekerjaanByBidangPatch("03")).thenReturn(List.of());

        mockMvc.perform(patch("/Master/syncPekerjaanByBidangPatch")
                        .contentType("application/json")
                        .content("{\"bidangCode\":\"03\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(masterUseCase).syncPekerjaanByBidangPatch("03");
    }
}
