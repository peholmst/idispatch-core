package net.pkhapps.idispatch.core.alert.client.api;

import net.pkhapps.idispatch.core.client.support.Registration;
import net.pkhapps.idispatch.core.client.support.ServiceClient;
import org.jetbrains.annotations.NotNull;

/**
 * Client interface for receiving alerts and text messages. This is designed to be used by mobile clients, station
 * monitors, command applications, etc. The user credentials used to connect to the alert service determine which
 * alerts/messages are received - the client has no control over this.
 */
public interface AlertClient extends ServiceClient {

    /**
     * Adds an {@link AlertListener} to be notified when an alert is received.
     *
     * @param alertListener the listener to register
     * @return a {@linkplain Registration registration handle} that can be used to unregister the listener
     */
    @NotNull Registration addAlertListener(@NotNull AlertListener alertListener);

    /**
     * Adds a {@link TextMessageListener} to be notified when a text message is received.
     *
     * @param textMessageListener the listener to register
     * @return a {@linkplain Registration registration handle} that can be used to unregister the listener
     */
    @NotNull Registration addTextMessageListener(@NotNull TextMessageListener textMessageListener);
}
