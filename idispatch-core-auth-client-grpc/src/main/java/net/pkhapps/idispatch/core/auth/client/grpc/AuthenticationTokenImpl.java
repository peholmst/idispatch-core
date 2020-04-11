package net.pkhapps.idispatch.core.auth.client.grpc;

import io.grpc.Metadata;
import net.pkhapps.idispatch.core.client.support.grpc.AuthenticationToken;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.time.Instant;
import java.util.Arrays;

/**
 * Implementation of {@link AuthenticationToken} that is populated from a gRPC authentication token.
 */
final class AuthenticationTokenImpl implements AuthenticationToken {

    private final Instant validFrom;
    private final Instant validTo;
    private final byte[] token;
    private final String fingerprint;

    AuthenticationTokenImpl(@NotNull net.pkhapps.idispatch.core.auth.proto.AuthenticationToken token) {
        this.validFrom = Instant.ofEpochSecond(token.getTokenValidFrom().getSeconds(),
                token.getTokenValidFrom().getNanos());
        this.validTo = Instant.ofEpochSecond(token.getTokenValidTo().getSeconds(),
                token.getTokenValidTo().getNanos());
        this.token = token.getToken().toByteArray();

        // compute fingerprint
        var hash = DigestUtils.digest(DigestUtils.getSha256Digest(), this.token);
        fingerprint = Hex.encodeHexString(hash);
    }

    @Override
    public String fingerprint() {
        return fingerprint;
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

    @TestOnly
    byte[] getToken() {
        return Arrays.copyOf(token, token.length);
    }
}
