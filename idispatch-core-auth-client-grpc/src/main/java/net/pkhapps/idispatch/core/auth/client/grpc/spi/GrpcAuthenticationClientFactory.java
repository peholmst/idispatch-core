package net.pkhapps.idispatch.core.auth.client.grpc.spi;

import net.pkhapps.idispatch.core.auth.client.api.AuthenticationClient;
import net.pkhapps.idispatch.core.auth.client.api.spi.AuthenticationClientFactory;
import net.pkhapps.idispatch.core.auth.client.grpc.GrpcAuthenticationClient;
import net.pkhapps.idispatch.core.client.support.api.Context;
import net.pkhapps.idispatch.core.client.support.grpc.GrpcContext;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of {@link AuthenticationClientFactory} that creates {@link GrpcAuthenticationClient}s.
 * The {@link Context} must be an instance of {@link GrpcContext}.
 */
public final class GrpcAuthenticationClientFactory implements AuthenticationClientFactory {

    @Override
    public @NotNull AuthenticationClient createAuthenticationClient(@NotNull Context context) {
        if (!(context instanceof GrpcContext)) {
            throw new IllegalArgumentException("The context must be an instance of GrpcContext");
        }
        return new GrpcAuthenticationClient((GrpcContext) context);
    }
}
