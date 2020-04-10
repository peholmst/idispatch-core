package net.pkhapps.idispatch.core.client.support.grpc;

import io.grpc.Metadata;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * TODO document me
 */
public interface AuthenticationToken {

    void addToHeaders(@NotNull Metadata.Key<byte[]> key, @NotNull Metadata headers);

    /**
     * including
     *
     * @return
     */
    @NotNull Instant validFrom();

    /**
     * excluding
     *
     * @return
     */
    @NotNull Instant validTo();
}
