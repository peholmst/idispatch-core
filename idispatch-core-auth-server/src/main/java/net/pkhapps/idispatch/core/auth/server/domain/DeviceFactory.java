package net.pkhapps.idispatch.core.auth.server.domain;

import net.pkhapps.idispatch.core.server.support.domain.Factory;
import net.pkhapps.idispatch.core.server.support.security.TenantId;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me
 */
public class DeviceFactory implements Factory<Device> {

    public @NotNull Device newDevice(@NotNull TenantId tenant,
                                     @NotNull String deviceId,
                                     @NotNull String displayName) {
        throw new UnsupportedOperationException("not implemented");
    }
}
