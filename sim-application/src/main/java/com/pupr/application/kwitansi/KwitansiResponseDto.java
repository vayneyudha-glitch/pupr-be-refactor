package com.pupr.application.kwitansi;

import com.pupr.application.bap.BapDto;
import com.pupr.application.master.MasterDto;

import java.util.Map;

public record KwitansiResponseDto(
        BapDto bap,
        MasterDto master,
        KwitansiDto kwitansi,
        Map<String, Object> masterMitra) {
}
