package net.pkhapps.idispatch.core.alert.client.api;

import net.pkhapps.idispatch.core.client.support.MultilingualString;
import net.pkhapps.idispatch.core.client.support.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

/**
 * Interface defining an alert. Alerts are used when dispatching resource to assignments.
 */
public interface Alert extends Message {

    /**
     * The assignment that the alert concerns.
     */
    @NotNull Assignment assignment();

    /**
     * Enumeration of assignment priorities.
     */
    enum Priority {
        A, B, C, D, N;
    }

    /**
     * Interface defining the details of an assignment.
     */
    interface Assignment {

        /**
         * The ID of the assignment. Alerts sent for the same assignment will have the same assignment ID.
         */
        @NotNull ObjectId assignmentId();

        /**
         * The date and time on which the assignment was opened.
         */
        @NotNull Instant openedOn();

        /**
         * The assignment code (such as '402').
         */
        @NotNull String code();

        /**
         * The assignment description (such as 'structure fire').
         */
        @NotNull MultilingualString description();

        /**
         * The assignment priority.
         */
        @NotNull Priority priority();

        /**
         * The assignment location.
         */
        @NotNull Location location();

        /**
         * Any additional details about the assignment.
         */
        @NotNull String details();

        /**
         * The call signs of all the resources currently assigned to the assignment.
         */
        @NotNull Collection<String> resources();
    }

    /**
     * Interface defining the location of an {@link Assignment}.
     *
     * @see AddressLocation
     * @see Intersection
     */
    interface Location {

        /**
         * The coordinates of the location.
         */
        @NotNull Point coordinates();

        /**
         * The name of the municipality that the location resides in.
         */
        @NotNull Optional<MultilingualString> municipality();

        /**
         * The name of the location if it has one, such as an island, a factory, a shopping mall, etc.
         */
        @NotNull Optional<MultilingualString> name();

        /**
         * Any additional details regarding the location entered by the dispatcher.
         */
        @NotNull String details();

        /**
         * Whether the location is exact (true) or approximate (false).
         */
        boolean isExact();
    }

    /**
     * Location that has a named address, typically a road name or an address point name and optionally a number.
     */
    interface AddressLocation extends Location {

        /**
         * The address (typically a road-, street- or address point name).
         */
        @NotNull MultilingualString address();

        /**
         * An optional address number.
         */
        @NotNull Optional<String> number();
    }

    /**
     * Location representing an intersection between two named roads.
     */
    interface Intersection extends Location {

        /**
         * The name of the first road in the intersection.
         */
        @NotNull MultilingualString firstRoad();

        /**
         * The name of the second road in the intersection.
         */
        @NotNull MultilingualString secondRoad();
    }
}
