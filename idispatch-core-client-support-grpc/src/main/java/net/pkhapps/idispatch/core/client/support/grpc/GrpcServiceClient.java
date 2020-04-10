package net.pkhapps.idispatch.core.client.support.grpc;

import net.pkhapps.idispatch.core.client.support.api.ServiceClient;
import net.pkhapps.idispatch.core.client.support.api.ServiceStatus;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Implement me!
 */
public abstract class GrpcServiceClient implements ServiceClient {

    public GrpcServiceClient(@NotNull GrpcContext grpcContext) {

    }

    @Override
    public @NotNull ServiceStatus serviceStatus() {
        return null;
    }
}
