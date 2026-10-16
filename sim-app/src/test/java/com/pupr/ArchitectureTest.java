package com.pupr;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Module-boundary rules for the pupr-be reactor (01-SKELETON.md section 2).
 *
 * <p>Dependencies must point inward:
 * {@code sim-web/sim-gateways/sim-persistence -> sim-application -> sim-domain}.
 * The rules currently pass trivially on the empty scaffold — they exist so the
 * first ported slice cannot violate the architecture unnoticed.</p>
 */
@AnalyzeClasses(packages = "com.pupr", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    /** D11: sim-domain stays free of Spring / JPA / Servlet / Hibernate imports. */
    @ArchTest
    static final ArchRule domain_is_framework_free = noClasses()
            .that().resideInAPackage("com.pupr.domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "org.springframework..",
                    "jakarta.persistence..",
                    "jakarta.servlet..",
                    "org.hibernate..",
                    "org.flywaydb..");

    /** sim-application talks to ports only — it must not reach into the adapters. */
    @ArchTest
    static final ArchRule application_depends_only_on_domain = noClasses()
            .that().resideInAPackage("com.pupr.application..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "com.pupr.web..",
                    "com.pupr.persistence..",
                    "com.pupr.gateways..");

    /** Adapters are siblings: web must not reach into persistence or gateways. */
    @ArchTest
    static final ArchRule web_does_not_reach_sibling_adapters = noClasses()
            .that().resideInAPackage("com.pupr.web..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "com.pupr.persistence..",
                    "com.pupr.gateways..");

    /** Adapters are siblings: persistence must not reach into web or gateways. */
    @ArchTest
    static final ArchRule persistence_does_not_reach_sibling_adapters = noClasses()
            .that().resideInAPackage("com.pupr.persistence..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "com.pupr.web..",
                    "com.pupr.gateways..");

    /** Adapters are siblings: gateways must not reach into web or persistence. */
    @ArchTest
    static final ArchRule gateways_does_not_reach_sibling_adapters = noClasses()
            .that().resideInAPackage("com.pupr.gateways..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "com.pupr.web..",
                    "com.pupr.persistence..");

    /** No dependency cycles between the modules. */
    @ArchTest
    static final ArchRule modules_are_free_of_cycles =
            SlicesRuleDefinition.slices().matching("com.pupr.(*)..").should().beFreeOfCycles();
}
