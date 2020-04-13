package net.pkhapps.idispatch.core.server.support.security;

/**
 * TODO Document me
 */
public class AuthenticationRequiredException extends RuntimeException {

    public AuthenticationRequiredException() {
        super("Authentication required");
    }
}
