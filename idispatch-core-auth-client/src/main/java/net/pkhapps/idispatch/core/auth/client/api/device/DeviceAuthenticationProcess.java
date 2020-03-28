package net.pkhapps.idispatch.core.auth.client.api.device;

import net.pkhapps.idispatch.core.auth.client.api.AuthenticationProcess;
import org.jetbrains.annotations.NotNull;

/**
 * Authentication process for authenticating devices (in cases where identifying the device, such as a vehicle console,
 * is more relevant than identifying the user using the device).
 */
public interface DeviceAuthenticationProcess extends AuthenticationProcess {

    /**
     * Sets the device secret ("password") to use for authentication. This string should be stored only on the
     * device itself and on the server. It should never need to be entered by a human user except maybe when setting
     * up the device (unless it can be transferred in some other way, e.g. through a QR code).
     *
     * @param secret the device secret
     */
    void setDeviceSecret(@NotNull String secret);
}
