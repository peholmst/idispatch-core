package net.pkhapps.idispatch.core.auth.client.api;

import org.jetbrains.annotations.NotNull;

/**
 * Base interface for authentication requests. You should use different implementations depending on the type of
 * user and/or authentication method.
 *
 * @param <P> the type of the authentication process needed to complete the authentication request
 * @param <S> the type of the authenticated session created after successful authentication
 */
public interface AuthenticationRequest<P extends AuthenticationProcess<S>, S extends AuthenticatedSession> {

    /**
     * The ID of the tenant that the client wants to interact with.
     */
    @NotNull String tenantId();
}
