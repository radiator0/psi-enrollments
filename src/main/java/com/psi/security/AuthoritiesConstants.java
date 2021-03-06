package com.psi.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String STUDENT = "ROLE_STUDENT";

    public static final String LECTURER = "ROLE_LECTURER";

    private AuthoritiesConstants() {
    }
}
