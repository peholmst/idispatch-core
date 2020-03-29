package net.pkhapps.idispatch.core.status.client.api;

import net.pkhapps.idispatch.core.client.support.api.ChangeListener;
import net.pkhapps.idispatch.core.client.support.api.Registration;
import net.pkhapps.idispatch.core.client.support.api.ServiceClient;
import org.jetbrains.annotations.NotNull;
import org.locationtech.jts.geom.Point;

import java.util.Collection;
import java.util.Optional;

/**
 * Client interface for sending statuses for a single resource. This is designed to be used by mobile clients whose
 * user credentials can be mapped to a particular resource. The credentials used to connect to the status service
 * determine the resource whose status is updated - the client has no control over this.
 */
public interface StatusClient extends ServiceClient {

    /**
     * Sends the geographical location of the resource to the status service.
     *
     * @param coordinates the coordinates to send
     */
    void sendLocation(@NotNull Point coordinates);

    /**
     * Gets all user assignable statuses. These statuses should typically show up as buttons in a user interface,
     * allowing the user to select which status to send. Once a status change has been accepted by the status service,
     * it will be become the resource's {@linkplain #currentStatus() current status}.
     *
     * @return an unmodifiable collection of {@link UserAssignableStatus}es
     */
    @NotNull Collection<UserAssignableStatus> statuses();

    /**
     * Gets the resource's current status.
     *
     * @return the status or an empty {@code Optional} if the resource has no status
     * @see #addCurrentStatusChangeListener(ChangeListener)
     */
    @NotNull Optional<Status> currentStatus();

    /**
     * Adds a listener to be notified when the resource's current status is changed.
     *
     * @param changeListener the listener to register
     * @return a {@linkplain Registration registration handle} that can be used to unregister the listener
     */
    @NotNull Registration addCurrentStatusChangeListener(@NotNull ChangeListener<Status> changeListener);
}
