package net.pkhapps.idispatch.core.client.support;

/**
 * Interface for a registration handle that is used to remove the registration. This is typically used for listeners.
 * When the listener is no longer needed, its registration is removed.
 */
public interface Registration {

    /**
     * Removes the registration.
     */
    void remove();
}
