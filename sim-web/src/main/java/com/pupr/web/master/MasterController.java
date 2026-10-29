package com.pupr.web.master;

import com.pupr.application.master.MasterDto;
import com.pupr.application.master.MasterUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Master")
public class MasterController {

    // TODO(parity): Defer getRealisationReport and getDashBoardInformation to the reporting slice.

    private final MasterUseCase masterUseCase;

    public MasterController(MasterUseCase masterUseCase) {
        this.masterUseCase = masterUseCase;
    }

    @PostMapping("/submit")
    public ResponseEntity<MasterDto> submit(@RequestBody MasterDto request) {
        return ResponseEntity.ok(masterUseCase.submit(request));
    }

    @PostMapping("/submitAll")
    public ResponseEntity<List<MasterDto>> submitAll(@RequestBody List<MasterDto> request) {
        return ResponseEntity.ok(masterUseCase.submitAll(request));
    }

    @GetMapping("/getById")
    public ResponseEntity<MasterDto> getById(@RequestParam String id) {
        return ResponseEntity.ok(masterUseCase.getById(id));
    }

    @PutMapping("/removeById")
    public ResponseEntity<MasterDto> removeById(@RequestParam String id) {
        return ResponseEntity.ok(masterUseCase.removeById(id));
    }

    @GetMapping("/getAllByTahunAnggaran")
    public ResponseEntity<List<MasterDto>> getAllByTahunAnggaran(
            @RequestParam Map<String, String> params) {
        return ResponseEntity.ok(masterUseCase.getAllByTahunAnggaran(params));
    }

    @GetMapping("/getMasterTrxList")
    public ResponseEntity<List<MasterDto>> getMasterTrxList(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(masterUseCase.getMasterTrxList(params));
    }

    @PostMapping("/submitSpkMaster")
    public ResponseEntity<MasterDto> submitSpkMaster(@RequestBody MasterDto request) {
        return ResponseEntity.ok(masterUseCase.submitSpkMaster(request));
    }

    @GetMapping("/getBySpkNo")
    public ResponseEntity<List<MasterDto>> getBySpkNo(@RequestParam String spkNo) {
        return ResponseEntity.ok(masterUseCase.getBySpkNo(spkNo));
    }

    @GetMapping("/syncPekerjaanByBidang")
    public ResponseEntity<List<MasterDto>> syncPekerjaanByBidang(@RequestParam String bidangCode) {
        return ResponseEntity.ok(masterUseCase.syncPekerjaanByBidang(bidangCode));
    }

    @PatchMapping("/syncPekerjaanByBidangPatch")
    public ResponseEntity<List<MasterDto>> syncPekerjaanByBidangPatch(
            @RequestBody SyncBidangRequest request) {
        return ResponseEntity.ok(masterUseCase.syncPekerjaanByBidangPatch(request.bidangCode()));
    }
}
