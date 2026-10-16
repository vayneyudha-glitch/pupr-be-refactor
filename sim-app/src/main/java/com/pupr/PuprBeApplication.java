package com.pupr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * pupr-be application entry point.
 *
 * <p>Single deployable assembled from the six {@code sim-*} modules
 * (see 01-SKELETON.md section 2). The only stateful dependency in the target
 * runtime is PostgreSQL 17.</p>
 */
@SpringBootApplication
public class PuprBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PuprBeApplication.class, args);
    }
}
