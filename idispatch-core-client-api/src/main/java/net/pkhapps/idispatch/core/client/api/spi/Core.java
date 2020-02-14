package net.pkhapps.idispatch.core.client.api.spi;

import net.pkhapps.idispatch.core.client.api.auth.AuthenticationService;
import org.jetbrains.annotations.NotNull;

/**
 * Provides access to the iDispatch Core Client services.
 *
 * @see CoreFactory
 */
public interface Core {

    /**
     * The {@link AuthenticationService} for authenticating users of iDispatch Core
     */
    @NotNull AuthenticationService getAuthenticationService();

    // TODO Define services
}
