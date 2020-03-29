package net.pkhapps.idispatch.core.auth.client.api;

import net.pkhapps.idispatch.core.client.support.MultilingualString;
import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown when an invalid principal (such as a username) was provided.
 */
public class InvalidPrincipalException extends AuthenticationException {

    public InvalidPrincipalException(@NotNull MultilingualString message) {
        super(message);
    }
}
