package net.pkhapps.idispatch.core.status.client.api;

import net.pkhapps.idispatch.core.client.support.MultilingualString;
import net.pkhapps.idispatch.core.client.support.ObjectId;
import org.jetbrains.annotations.NotNull;

/**
 * Interface defining a status that a resource can be in. Some statuses are automatically assigned by the system
 * when certain things happen whereas other statuses are {@linkplain UserAssignableStatus user assignable}.
 * <p>
 * Statuses are comparable to make it easier to show them in the correct order in a user interface.
 */
public interface Status extends Comparable<Status> {

    /**
     * The ID of the status. Each status has a unique ID.
     */
    @NotNull ObjectId statusId();

    /**
     * The name of the status.
     */
    @NotNull MultilingualString name();

    /**
     * The color of the status in the form of a six character hex-string (RRGGBB).
     */
    @NotNull String color();
}
