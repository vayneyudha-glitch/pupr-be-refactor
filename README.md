# pupr-be

PUPR SIM backend — target architecture: **modular monolith, ports & adapters**.
Java 25 · Spring Boot 4.0.5 · Spring Modulith 2.0.6 · PostgreSQL 17 (Spring Data JPA + Flyway).

Replaces the legacy four-module build (`CoreApplication`/`Model`/`Repository`/`Transaction`,
Spring Boot 2.7.1, Java 8) of the `uat` branch.

Planning docs (in the parent folder):

- `../01-SKELETON.md` — physical structure: module graph, route inventory, defects to eliminate
- `../02-Product_Service_Architecture.md` — business view
- `../03-System_Technical_Architecture.txt` — runtime stack, data architecture
- `../04-Migration_Runbook.txt` — phased migration plan (this scaffold is Phase 1)

## Status

The `MasterTransaction` CRUD slice is implemented with the legacy `/Master/...` routes and
request/response fields. `getMasterTrxList` retains the legacy internal pekerjaan sync when a
`bidang` parameter is supplied. Standalone pekerjaan sync routes are also implemented with the
legacy GET and PATCH behavior. Reporting remains deferred because it depends on BAP.

Verified 2026-10-17: `mvn -Djava.version=21 clean verify` → BUILD SUCCESS, 7/7 tests green
(6 ArchUnit rules + Spring Modulith module verification), executable boot jar produced.
The committed target stays Java 25; the override only relaxes `--release` until JDK 25 is installed.

## Modules

Dependencies point inward: `sim-app` → {`sim-web`, `sim-gateways`, `sim-persistence`} → `sim-application` → `sim-domain`.

- **sim-domain** — domain model, ports, domain rules. No Spring/JPA/Web imports (D11).
- **sim-application** — use cases, DTOs, mapping (MapStruct, compile-time). Talks to ports only.
- **sim-persistence** — the only module that knows SQL. Spring Data JPA + Flyway (`src/main/resources/db/migration`). JPA entities live here; the domain model stays pure (D9).
- **sim-gateways** — outbound integrations: the legacy pekerjaan lookup used by
  `getMasterTrxList`, plus JasperReports. Other CRS integrations remain subject to D12.
- **sim-web** — controllers, security, error mapping. Legacy route surface preserved byte-compatible.
- **sim-app** — boot class, profiles, `application.yml`, ArchUnit + Modulith tests.

The first Flyway migration creates `master_transaction`. The legacy `Master` model has no
`@DBRef`; `mitraId` is a scalar string and is therefore not given a speculative SQL foreign key.
The pekerjaan sync gateway defaults to the legacy service endpoint and can be overridden with
`PUPR_MASTER_SERVICE_URL`.

## Build

Requires **JDK 25** (target) and Maven 3.9.x:

```
mvn clean verify
```

If only an older JDK is installed (current local machine has JDK 21), the reactor can be
verified with a temporary override that relaxes `--release`:

```
mvn -Djava.version=21 clean verify
```

Install JDK 25 to build with the committed target.

## Run

```
mvn -pl sim-app -am spring-boot:run
```

Requires PostgreSQL 17. Configuration via environment variables (defaults in parentheses):

- `PUPR_DB_URL` (`jdbc:postgresql://localhost:5432/pupr`)
- `PUPR_DB_USERNAME` (`pupr`) · `PUPR_DB_PASSWORD` (`pupr`)
- `PUPR_SERVER_PORT` (`8080`)

Flyway owns the schema (`ddl-auto=validate`, never `update`). The first migration is still
to be written — the app will not start against an empty database until it exists.

## Open decisions (04-Migration_Runbook.txt §1)

D8 ID strategy · D12 CRS port-or-delete · D13 recap entities · D14 audit starter ·
D15 event delivery model. None block this scaffold; resolve each before porting behaviour.

## Deferred on purpose

- springdoc-openapi — added with the first controller (verify the Boot 4-compatible line)
- security configuration in `sim-web` (legacy semantics: stateless, CSRF off, CORS on, `permitAll`)
- Testcontainers-backed integration tests (dependencies already declared)
- the 83-handler route surface — ported per slice in later phases
- Maven wrapper (`mvnw`) — using the installed Maven 3.9.1 for now
