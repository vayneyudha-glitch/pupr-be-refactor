/**
 * sim-domain — the innermost layer of pupr-be.
 *
 * <p>Owns the domain model, ports (interfaces the outside world must implement or call)
 * and domain rules. This module must stay free of Spring, JPA, Servlet and other
 * framework imports (migration decision D11) — enforced by the architecture tests
 * in {@code sim-app}.</p>
 *
 * <p>Lombok is the only permitted annotation dependency here; Java records replace
 * most previous usages.</p>
 */
package com.pupr.domain;
