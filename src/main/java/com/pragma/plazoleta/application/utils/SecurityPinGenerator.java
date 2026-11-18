
package com.pragma.plazoleta.application.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SecurityPinGenerator {

    private static final int PIN_BOUND = 1_000_000;

    private final SecureRandom secureRandom = new SecureRandom();

    public String generate() {
        return String.format("%06d",
                secureRandom.nextInt(PIN_BOUND));
    }
}
