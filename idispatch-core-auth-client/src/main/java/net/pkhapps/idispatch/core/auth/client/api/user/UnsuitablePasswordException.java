package net.pkhapps.idispatch.core.auth.client.api.user;

import net.pkhapps.idispatch.core.client.support.MultilingualException;
import net.pkhapps.idispatch.core.client.support.MultilingualString;
import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown by {@link AuthenticatedUserSession#changeUserPassword(String, String)} when the new password
 * does not meet the requirements.
 */
public class UnsuitablePasswordException extends MultilingualException {

    public UnsuitablePasswordException(@NotNull MultilingualString message) {
        super(message);
    }
}
