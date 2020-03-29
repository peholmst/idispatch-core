package net.pkhapps.idispatch.core.client.support.api;

import org.jetbrains.annotations.NotNull;

/**
 * Base interface for service clients.
 */
public interface ServiceClient {

    /**
     * The status of the service or the connection to the service.
     *
     * @return a {@link ServiceStatus} instance that remains the same for the lifecycle of this client instance.
     */
    @NotNull ServiceStatus serviceStatus();
}
