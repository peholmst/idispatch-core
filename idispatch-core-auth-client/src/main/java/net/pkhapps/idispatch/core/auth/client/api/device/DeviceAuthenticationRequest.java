package net.pkhapps.idispatch.core.auth.client.api.device;

import net.pkhapps.idispatch.core.auth.client.api.AuthenticatedSession;
import net.pkhapps.idispatch.core.auth.client.api.AuthenticationRequest;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * Request for authenticating devices such as mobile vehicle clients or station monitors.
 */
public class DeviceAuthenticationRequest implements AuthenticationRequest<DeviceAuthenticationProcess,
        AuthenticatedSession> {

    private final String tenantId;
    private final String deviceId;

    /**
     * Creates a new device authentication request for the given tenant and device.
     *
     * @param tenantId the tenant ID
     * @param deviceId the device ID
     */
    public DeviceAuthenticationRequest(@NotNull String tenantId, @NotNull String deviceId) {
        this.tenantId = requireNonNull(tenantId);
        this.deviceId = requireNonNull(deviceId);
    }

    @Override
    public @NotNull String tenantId() {
        return tenantId;
    }

    /**
     * The ID of the device that wants to authenticate.
     */
    public @NotNull String deviceId() {
        return deviceId;
    }
}
