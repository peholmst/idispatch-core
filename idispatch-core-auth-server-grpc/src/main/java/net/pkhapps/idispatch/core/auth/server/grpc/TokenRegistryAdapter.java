package net.pkhapps.idispatch.core.auth.server.grpc;

import net.pkhapps.idispatch.core.server.support.grpc.TokenRegistry;
import net.pkhapps.idispatch.core.server.support.security.AuthenticatedPrincipal;
import org.jetbrains.annotations.NotNull;

public class TokenRegistryAdapter implements TokenRegistry {

    @Override
    public @NotNull AuthenticatedPrincipal getPrincipalOfToken(byte[] token) {
        return null;
    }
}
