package net.pkhapps.idispatch.core.server.support.grpc;

import net.pkhapps.idispatch.core.server.support.security.AuthenticatedPrincipal;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me
 */
public interface TokenRegistry {

    /**
     * @param token the token provided by the client
     * @return the authenticated principal associated with the given token
     * @throws net.pkhapps.idispatch.core.server.support.security.AuthenticationRequiredException if the token is not
     *                                                                                            valid
     */
    @NotNull AuthenticatedPrincipal getPrincipalOfToken(byte[] token);
}
