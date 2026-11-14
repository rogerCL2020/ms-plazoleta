
package com.pragma.plazoleta.infrastructure.security;

public record AuthenticatedUser(
        Long userId,
        String email
) {
}
