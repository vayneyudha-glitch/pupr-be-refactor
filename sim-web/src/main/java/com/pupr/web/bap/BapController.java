package com.pupr.web.bap;

import com.pupr.application.bap.BapDto;
import com.pupr.application.bap.BapResponseDto;
import com.pupr.application.bap.BapUseCase;
import com.pupr.application.deletedcollection.DeletedCollectionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Bap")
public class BapController {

    private final BapUseCase bapUseCase;

    public BapController(BapUseCase bapUseCase) {
        this.bapUseCase = bapUseCase;
    }

    @PostMapping("/submit")
    public ResponseEntity<BapDto> submit(@RequestBody BapDto request) {
        // TODO(parity): Legacy audit aspects come from pupr-audit-starter, absent from legacy/uat; wait for D14.
        return ResponseEntity.ok(bapUseCase.submit(request));
    }

    @GetMapping("/submitValidItemanis")
    public ResponseEntity<BapDto> submitValidItemanis(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(bapUseCase.updateValidItemanis(params));
    }

    @GetMapping("/findById")
    public ResponseEntity<BapResponseDto> findById(@RequestParam String id) {
        return ResponseEntity.ok(bapUseCase.findById(id));
    }

    // TODO(parity): /findByNo is deferred until Ringkasan, Kwitansi, SPM, SP2D and MasterMitra are migrated.

    @GetMapping("/findByNoV2")
    public ResponseEntity<List<BapDto>> findByNoV2(@RequestParam String bapNo) {
        return ResponseEntity.ok(bapUseCase.findByBapNoV2(bapNo));
    }

    @GetMapping("/findDataByLimit1")
    public ResponseEntity<List<BapDto>> findDataByLimit1(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(bapUseCase.findByLimit1(params));
    }

    @GetMapping("/findDataByBapNoLimit")
    public ResponseEntity<List<BapDto>> findDataByBapNoLimit(@RequestParam String bapNo) {
        return ResponseEntity.ok(bapUseCase.findByBapNoLimit(bapNo));
    }

    @GetMapping("/findDataByMasterTrxId")
    public ResponseEntity<BapDto> findDataByMasterTrxId(@RequestParam String masterTrxId) {
        return ResponseEntity.ok(bapUseCase.findByMasterTrxId(masterTrxId));
    }

    @GetMapping("/findDataByMasterTrxIdV2")
    public ResponseEntity<List<BapDto>> findDataByMasterTrxIdV2(@RequestParam String masterTrxId) {
        return ResponseEntity.ok(bapUseCase.findByMasterTrxIdV2(masterTrxId));
    }

    @PutMapping("/removeById")
    public ResponseEntity<DeletedCollectionDto> removeById(@RequestParam Map<String, String> params) {
        // TODO(parity): Legacy audit aspects come from pupr-audit-starter, absent from legacy/uat; wait for D14.
        return ResponseEntity.ok(bapUseCase.removeById(params));
    }

    @GetMapping("/findBapNoHibah")
    public ResponseEntity<BapDto> findBapNoHibah(@RequestParam String noHibah) {
        return ResponseEntity.ok(bapUseCase.findOneByHibahNo(noHibah));
    }

    @GetMapping("/getRealisationReport")
    public ResponseEntity<List<BapDto>> getRealisationReport(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(bapUseCase.findByLimit1(params));
    }

    @GetMapping("/getRealisationView")
    public ResponseEntity<List<BapDto>> getRealisationView(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(bapUseCase.findByLimit1(params));
    }

    // TODO(parity): /getValidateRemove is deferred until Ringkasan, Kwitansi, SPP, SPM and SP2D are migrated.
    // TODO(parity): /findByBastp is deferred until Ringkasan is migrated.
}
