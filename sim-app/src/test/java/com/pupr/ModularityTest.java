package com.pupr;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

/**
 * Spring Modulith module verification (decision D4) over the boot class package:
 * every direct subpackage of {@code com.pupr} is treated as an application module.
 */
class ModularityTest {

    @Test
    void moduleStructureIsValid() {
        ApplicationModules.of(PuprBeApplication.class).verify();
    }
}
