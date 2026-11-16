package com.pupr.web.kwitansi;

import com.pupr.application.bap.BapDto;
import com.pupr.application.deletedcollection.DeletedCollectionDto;
import com.pupr.application.kwitansi.KwitansiDto;
import com.pupr.application.kwitansi.KwitansiResponseDto;
import com.pupr.application.kwitansi.KwitansiUseCase;
import com.pupr.domain.kwitansi.KwitansiDuplicateException;
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
@RequestMapping("/Kwitansi")
public class KwitansiController {

    private final KwitansiUseCase kwitansiUseCase;

    public KwitansiController(KwitansiUseCase kwitansiUseCase) {
        this.kwitansiUseCase = kwitansiUseCase;
    }

    @PostMapping("/submit")
    public ResponseEntity<KwitansiResponseDto> submit(@RequestBody KwitansiDto request) {
        // TODO(parity): Legacy request/error audit annotations depend on the absent audit starter.
        return ResponseEntity.ok(kwitansiUseCase.submit(request));
    }

    @PutMapping("/put")
    public ResponseEntity<KwitansiResponseDto> put(@RequestBody KwitansiDto request) throws Exception {
        // TODO(parity): Legacy request/error audit annotations depend on the absent audit starter.
        try {
            return ResponseEntity.ok(kwitansiUseCase.submit(request));
        } catch (KwitansiDuplicateException exception) {
            // TODO(parity): Legacy wraps PUT conflicts as generic exceptions instead of returning 409.
            throw new Exception(exception.getMessage());
        }
    }

    // TODO(parity): GET /Kwitansi/findById is deferred until the Ringkasan and MasterMitra ports have adapters.

    @GetMapping("/findByBapNo")
    public ResponseEntity<List<KwitansiDto>> findByBapNo(@RequestParam String bapNo) {
        return ResponseEntity.ok(kwitansiUseCase.findByBapNo(bapNo));
    }

    @GetMapping("/findDataByLimit1")
    public ResponseEntity<List<KwitansiDto>> findDataByLimit1(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(kwitansiUseCase.findByLimit1(params));
    }

    @GetMapping("/findDataByLimit1V2")
    public ResponseEntity<List<BapDto>> findDataByLimit1V2(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(kwitansiUseCase.findByLimit1V2(params));
    }

    @PutMapping("/removeById")
    public ResponseEntity<DeletedCollectionDto> removeById(@RequestParam Map<String, String> params) {
        // TODO(parity): Legacy request/error audit annotations depend on the absent audit starter.
        return ResponseEntity.ok(kwitansiUseCase.removeById(params));
    }

    // TODO(parity): GET /Kwitansi/item-report/{id} is deferred pending the JasperReports decision in sim-gateways.
}
