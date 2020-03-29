package net.pkhapps.idispatch.core.auth.client.api;

import net.pkhapps.idispatch.core.client.support.MultilingualException;
import net.pkhapps.idispatch.core.client.support.MultilingualString;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for authentication exceptions.
 */
public abstract class AuthenticationException extends MultilingualException {

    public AuthenticationException(@NotNull MultilingualString message) {
        super(message);
    }
}
