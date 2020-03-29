package net.pkhapps.idispatch.core.auth.client.api;

import net.pkhapps.idispatch.core.client.support.api.MultilingualString;
import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown because invalid credentials (such as a password) were provided.
 */
public class InvalidCredentialsException extends AuthenticationException {

    public InvalidCredentialsException(@NotNull MultilingualString message) {
        super(message);
    }
}
