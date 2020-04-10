package net.pkhapps.idispatch.core.auth.client.api;

import net.pkhapps.idispatch.core.client.support.api.MultilingualString;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Exception thrown when a session token cannot be refreshed for some reason.
 */
public class CannotRefreshTokenException extends AuthenticationException {

    public CannotRefreshTokenException() {
        this(new MultilingualString(Locale.ENGLISH, "Cannot refresh token")); // TODO Translate me
    }

    public CannotRefreshTokenException(@NotNull MultilingualString message) {
        super(message);
    }
}
