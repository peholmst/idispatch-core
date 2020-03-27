package net.pkhapps.idispatch.core.alert.client.api;

import net.pkhapps.idispatch.core.client.support.Registration;
import net.pkhapps.idispatch.core.client.support.ServiceClient;
import org.jetbrains.annotations.NotNull;

/**
 * Client interface for receiving alerts. This is designed to be used by mobile clients, station monitors, command
 * applications, etc. The user credentials used to connect to the alert service determine which alerts are received.
 * The client has no control over this.
 */
public interface AlertClient extends ServiceClient {

    /**
     * Adds an {@link AlertListener} to be notified when an alert is received.
     *
     * @param alertListener the listener to register
     * @return a {@linkplain Registration registration handle} that can be used to unregister the listener
     */
    @NotNull Registration addAlertListener(@NotNull AlertListener alertListener);
}
