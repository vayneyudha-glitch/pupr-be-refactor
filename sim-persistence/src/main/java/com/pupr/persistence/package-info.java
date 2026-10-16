/**
 * sim-persistence — the only module that knows SQL.
 *
 * <p>Spring Data JPA over PostgreSQL 17. JPA entities ({@code *JpaEntity}) live here so
 * the domain model stays pure (decision D9) — one annotated entity per aggregate.
 * Flyway owns the schema; migrations live in {@code src/main/resources/db/migration}
 * and are reviewed like code ({@code ddl-auto=validate}, never {@code update}).</p>
 */
package com.pupr.persistence;
