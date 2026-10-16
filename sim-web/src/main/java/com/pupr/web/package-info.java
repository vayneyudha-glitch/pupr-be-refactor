/**
 * sim-web — inbound HTTP adapters.
 *
 * <p>Controllers, security configuration and error mapping. The 14 legacy route
 * prefixes and 83 handlers (see 01-SKELETON.md section 1.1) must be preserved
 * byte-compatible; security keeps the legacy semantics: stateless, CSRF off,
 * CORS on, {@code permitAll}.</p>
 */
package com.pupr.web;
