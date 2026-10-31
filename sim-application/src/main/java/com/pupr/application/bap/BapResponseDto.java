package com.pupr.application.bap;

import com.pupr.application.master.MasterDto;

import java.util.Map;

public record BapResponseDto(
        BapDto bap,
        MasterDto master,
        Map<String, Object> ringkasan,
        Map<String, Object> kwitansi,
        Map<String, Object> masterMitra,
        Map<String, Object> spp,
        Map<String, Object> spm,
        Map<String, Object> sp2d) {
}
