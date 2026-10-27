package com.pupr.domain;

public class MasterNotFoundException extends RuntimeException {

    public MasterNotFoundException(String message) {
        super(message);
    }
}
