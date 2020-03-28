package net.pkhapps.idispatch.core.auth.client.api;

import net.pkhapps.idispatch.core.client.support.ServiceClient;
import org.jetbrains.annotations.NotNull;

/**
 * Client interface for authenticating users. This is typically the first client you will have to interact with, as
 * all the other clients require an authenticated user to work.
 */
public interface AuthenticationClient extends ServiceClient {

    /**
     * Starts an authentication process. The returned process can then be used to complete the authentication.
     *
     * @param authenticationRequest the authentication request
     * @param <P>                   the type of the authentication process (depends on the request)
     * @return the authentication process
     * @see AuthenticationProcess#authenticate()
     */
    <P extends AuthenticationProcess>
    @NotNull P startAuthentication(@NotNull AuthenticationRequest<P> authenticationRequest);
}
