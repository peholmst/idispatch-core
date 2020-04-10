package net.pkhapps.idispatch.core.auth.client.grpc;

import net.pkhapps.idispatch.core.auth.client.api.AuthenticatedPrincipal;
import net.pkhapps.idispatch.core.auth.proto.DevicePrincipal;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link AuthenticatedPrincipal} that represents a device authenticated over gRPC.
 */
final class GrpcAuthenticatedDevice implements AuthenticatedPrincipal {

    private final String tenantId;
    private final String deviceId;
    private final String displayName;
    private final Set<String> authorities;

    GrpcAuthenticatedDevice(@NotNull DevicePrincipal principal) {
        tenantId = principal.getTenantId();
        displayName = principal.getDisplayName();
        deviceId = principal.getDeviceId();
        authorities = new HashSet<>(principal.getAuthorityList());
    }

    @Override
    public @NotNull String tenantId() {
        return tenantId;
    }

    @Override
    public @NotNull String displayName() {
        return displayName;
    }

    @Override
    public boolean hasAuthority(@NotNull String authority) {
        return authorities.contains(authority);
    }

    @Override
    public String getName() {
        return deviceId;
    }
}
