package com.pupr;

import com.pupr.application.kwitansi.KwitansiDto;
import com.pupr.application.kwitansi.KwitansiResponseDto;
import com.pupr.application.kwitansi.KwitansiUseCase;
import com.pupr.domain.kwitansi.KwitansiYearValidationException;
import com.pupr.web.kwitansi.KwitansiController;
import com.pupr.web.kwitansi.KwitansiErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class KwitansiControllerTest {

    private KwitansiUseCase kwitansiUseCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        kwitansiUseCase = mock(KwitansiUseCase.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new KwitansiController(kwitansiUseCase))
                .setControllerAdvice(new KwitansiErrorHandler())
                .build();
    }

    @Test
    void submitUsesLegacyRouteAndMapsForbiddenYearValidation() throws Exception {
        when(kwitansiUseCase.submit(any())).thenThrow(new KwitansiYearValidationException(
                "Transaction Blocked: Cannot modify data for year "));

        mockMvc.perform(post("/Kwitansi/submit")
                        .contentType("application/json")
                        .content("{\"masterTransactionId\":\"4\",\"bapNo\":\"BAP-4\"}"))
                .andExpect(status().isForbidden())
                .andExpect(content().json("""
                        {"status":403,"error":"Forbidden","message":"Transaction Blocked: Cannot modify data for year ","path":"/Kwitansi/submit"}
                        """));
    }

    @Test
    void putUsesLegacyMethodAndPath() throws Exception {
        when(kwitansiUseCase.submit(any())).thenReturn(new KwitansiResponseDto(null, null, null, null));

        mockMvc.perform(put("/Kwitansi/put")
                        .contentType("application/json")
                        .content("{\"id\":\"17\",\"bapNo\":\"BAP-4\"}"))
                .andExpect(status().isOk());

        verify(kwitansiUseCase).submit(any(KwitansiDto.class));
    }

    @Test
    void findByBapNoKeepsLegacyQueryParameter() throws Exception {
        when(kwitansiUseCase.findByBapNo("BAP-4")).thenReturn(List.of());

        mockMvc.perform(get("/Kwitansi/findByBapNo").param("bapNo", "BAP-4"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(kwitansiUseCase).findByBapNo("BAP-4");
    }
}
