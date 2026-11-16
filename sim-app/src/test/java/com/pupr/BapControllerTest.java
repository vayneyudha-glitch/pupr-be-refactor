package com.pupr;

import com.pupr.application.bap.BapDto;
import com.pupr.application.bap.BapUseCase;
import com.pupr.domain.bap.BapYearValidationException;
import com.pupr.web.bap.BapController;
import com.pupr.web.bap.BapErrorHandler;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BapControllerTest {

    private BapUseCase bapUseCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        bapUseCase = mock(BapUseCase.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new BapController(bapUseCase))
                .setControllerAdvice(new BapErrorHandler())
                .build();
    }

    @Test
    void findByMasterTransactionIdV2KeepsLegacyPathAndParameterName() throws Exception {
        when(bapUseCase.findByMasterTrxIdV2("17")).thenReturn(List.of());

        mockMvc.perform(get("/Bap/findDataByMasterTrxIdV2").param("masterTrxId", "17"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(bapUseCase).findByMasterTrxIdV2("17");
    }

    @Test
    void submitUsesLegacyPostRouteAndMapsForbiddenYearValidation() throws Exception {
        when(bapUseCase.submit(any())).thenThrow(new BapYearValidationException(
                "Transaction Blocked: Cannot modify data for year "));

        mockMvc.perform(post("/Bap/submit")
                        .contentType("application/json")
                        .content("{\"masterTransactionId\":\"4\",\"bapNo\":\"BAP-4\"}"))
                .andExpect(status().isForbidden())
                .andExpect(content().json("""
                        {"status":403,"error":"Forbidden","message":"Transaction Blocked: Cannot modify data for year ","path":"/Bap/submit"}
                        """));
    }
}
