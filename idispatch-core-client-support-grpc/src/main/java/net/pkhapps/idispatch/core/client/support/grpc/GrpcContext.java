package net.pkhapps.idispatch.core.client.support.grpc;

import io.grpc.Channel;
import net.pkhapps.idispatch.core.client.support.api.Context;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * TODO Document and implement me
 */
public class GrpcContext implements Context {

    private final Channel channel;

    public GrpcContext(@NotNull Channel channel) {
        this.channel = requireNonNull(channel);
    }

    public @NotNull Channel channel() {
        return channel;
    }
}
