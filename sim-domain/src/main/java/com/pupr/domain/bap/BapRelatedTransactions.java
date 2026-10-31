package com.pupr.domain.bap;

import java.util.Map;
import java.util.Optional;

/**
 * Boundary for transaction modules that are not migrated yet.
 *
 * TODO(parity): Implement these lookups when Ringkasan, Kwitansi, SPP, SPM, SP2D, Validasi,
 * Rekom and MasterMitra are migrated. The legacy BAP detail, remove-validation and BASTP routes
 * depend on these transactions.
 *
 * TODO(parity): The legacy child-link switch falls through from VALIDASI to SP2D and swallows
 * exceptions. The remove-validation service also dereferences missing BAP/child values and
 * unboxes a nullable Boolean; preserve those outcomes when the deferred routes are ported.
 */
public interface BapRelatedTransactions {

    Optional<Map<String, Object>> findRingkasanByBapNo(String bapNo);

    Optional<Map<String, Object>> findKwitansiByBapNo(String bapNo);

    Optional<Map<String, Object>> findSppByBapNo(String bapNo);

    Optional<Map<String, Object>> findSpmByBapNo(String bapNo);

    Optional<Map<String, Object>> findSp2dByBapNo(String bapNo);

    Optional<Map<String, Object>> findValidasiByBapNo(String bapNo);

    Optional<Map<String, Object>> findRekomByBapNo(String bapNo);

    Optional<Map<String, Object>> findMasterMitraById(String masterMitraId);

    Optional<Map<String, Object>> findRingkasanByBastpNo(String noTrx);
}
