package com.pupr.domain.kwitansi;

import java.util.Map;
import java.util.Optional;

/**
 * Port for the deferred Kwitansi detail integration.
 *
 * TODO(parity): Implement this lookup when Ringkasan is migrated; the legacy detail route looks
 * up Ringkasan by BAP ID. MasterMitra lookup can use BapRelatedTransactions.
 */
public interface KwitansiRelatedTransactions {

    Optional<Map<String, Object>> findRingkasanByBapId(String bapId);
}
