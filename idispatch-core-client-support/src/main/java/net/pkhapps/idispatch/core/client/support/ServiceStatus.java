package net.pkhapps.idispatch.core.client.support;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for tracking the status of a particular service. This can be used to alert users or administrators if
 * a vital service goes down, e.g. due to a network connection problem or a server outage.
 */
public interface ServiceStatus {

    /**
     * The current state of the service or the connection to the service (from the client's point of view it does
     * not matter).
     *
     * @return the state.
     */
    @NotNull State state();

    /**
     * Adds a listener to be notified when the {@linkplain #state() state} changes.
     *
     * @param stateChangeListener the listener to register.
     * @return a {@linkplain Registration registration handle} that can be used to unregister the listener.
     */
    @NotNull Registration addStateChangeListener(@NotNull ChangeListener<State> stateChangeListener);

    /**
     * Enumeration of service states.
     */
    enum State {
        /**
         * The service is up and working properly.
         */
        UP,
        /**
         * The service is down and unusable.
         */
        DOWN,
        /**
         * The state of the service is unknown. This is typically a transitional state, for example during startup or
         * while establishing a connection.
         */
        UNKNOWN
    }
}
