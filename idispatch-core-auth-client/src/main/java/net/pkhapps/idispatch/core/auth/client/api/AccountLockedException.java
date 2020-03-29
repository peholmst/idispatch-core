package net.pkhapps.idispatch.core.auth.client.api;

import net.pkhapps.idispatch.core.client.support.MultilingualString;
import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown when attempting to authenticate as an account that is locked.
 */
public class AccountLockedException extends AuthenticationException {

    public AccountLockedException(@NotNull MultilingualString message) {
        super(message);
    }
}
