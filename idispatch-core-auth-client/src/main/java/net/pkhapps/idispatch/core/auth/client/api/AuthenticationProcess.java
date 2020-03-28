package net.pkhapps.idispatch.core.auth.client.api;

import org.jetbrains.annotations.NotNull;

/**
 * Base interface for an authentication process. Different {@link AuthenticationRequest}s have different processes.
 * Clients should interact with the process until it has all the necessary information and then perform the
 * authentication by calling {@link #authenticate()}.
 */
public interface AuthenticationProcess {

    /**
     * Attempts to authenticate the user.
     *
     * @return an authenticated session if authentication was successful
     * @throws AuthenticationException if authentication fails
     */
    @NotNull AuthenticatedSession authenticate();
}
