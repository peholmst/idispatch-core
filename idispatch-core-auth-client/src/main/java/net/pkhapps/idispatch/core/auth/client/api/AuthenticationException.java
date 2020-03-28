package net.pkhapps.idispatch.core.auth.client.api;

import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown when authentication fails for some reason.
 *
 * @see AuthenticationProcess#authenticate()
 * @see AuthenticatedSession#refresh()
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
    }

    public AuthenticationException(@Nullable String message) {
        super(message);
    }
}
