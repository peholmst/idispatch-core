package net.pkhapps.idispatch.core.auth.client.grpc;

import io.grpc.Metadata;
import net.pkhapps.idispatch.core.client.support.grpc.AuthenticationToken;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * Implementation of {@link AuthenticationToken} that is populated from a gRPC authentication token.
 */
final class AuthenticationTokenImpl implements AuthenticationToken {

    private final Instant validFrom;
    private final Instant validTo;
    private final byte[] token;

    AuthenticationTokenImpl(@NotNull net.pkhapps.idispatch.core.auth.proto.AuthenticationToken token) {
        this.validFrom = Instant.ofEpochSecond(token.getTokenValidFrom().getSeconds(),
                token.getTokenValidFrom().getNanos());
        this.validTo = Instant.ofEpochSecond(token.getTokenValidTo().getSeconds(),
                token.getTokenValidTo().getNanos());
        this.token = token.getToken().toByteArray();
    }

    @Override
    public void addToHeaders(Metadata.@NotNull Key<byte[]> key, @NotNull Metadata headers) {
        headers.put(key, token);
    }

    @Override
    public @NotNull Instant validFrom() {
        return validFrom;
    }

    @Override
    public @NotNull Instant validTo() {
        return validTo;
    }
}
