package net.pkhapps.idispatch.core.auth.client.api;

import net.pkhapps.idispatch.core.client.support.api.MultilingualString;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * TODO Document me!
 */
public class InternalAuthenticationException extends AuthenticationException {

    public InternalAuthenticationException(@NotNull String message) {
        super(new MultilingualString(Locale.ENGLISH, message));
    }
}
