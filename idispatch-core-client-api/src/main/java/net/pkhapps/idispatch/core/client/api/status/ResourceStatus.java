package net.pkhapps.idispatch.core.client.api.status;

import net.pkhapps.idispatch.core.client.api.assignment.AssignmentId;
import net.pkhapps.idispatch.core.client.api.location.Location;
import net.pkhapps.idispatch.core.client.api.resource.ResourceId;
import net.pkhapps.idispatch.core.client.api.util.IdentifiableObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * The status of a resource consists of the following:
 * <ul>
 *     <li>its location (where is it right now?)</li>
 *     <li>its state (what is it doing right now?)</li>
 *     <li>its assignment (what assignment is it working with right now?)</li>
 *     <li>its staffing (how many fire fighters or other personnel does  it have right now?)</li>
 * </ul>
 *
 * @see Location
 * @see ResourceState
 * @see AssignmentId
 * @see Staffing
 */
public class ResourceStatus extends IdentifiableObject<ResourceId> {

    private final StatusInfo<Location> location;
    private final StatusInfo<ResourceStateId> state;
    private final StatusInfo<AssignmentId> assignment;
    private final StatusInfo<Staffing> staffing;

    public ResourceStatus(@NotNull ResourceId id,
                          @NotNull StatusInfo<Location> location,
                          @NotNull StatusInfo<ResourceStateId> state,
                          @NotNull StatusInfo<AssignmentId> assignment,
                          @NotNull StatusInfo<Staffing> staffing) {
        super(id);
        this.location = requireNonNull(location);
        this.state = requireNonNull(state);
        this.assignment = requireNonNull(assignment);
        this.staffing = requireNonNull(staffing);
    }

    /**
     * The last known/current location of the resource.
     */
    public @NotNull StatusInfo<Location> getLocation() {
        return location;
    }

    /**
     * The current state of the resource.
     */
    public @NotNull StatusInfo<ResourceStateId> getState() {
        return state;
    }

    /**
     * The current assignment of the resource.
     */
    public @NotNull StatusInfo<AssignmentId> getAssignment() {
        return assignment;
    }

    /**
     * The current staffing of the resource.
     */
    public @NotNull StatusInfo<Staffing> getStaffing() {
        return staffing;
    }

    /**
     * TODO Document me
     *
     * @param <S>
     */
    public static class StatusInfo<S> {
        private final S status;
        private final Instant lastUpdated;

        private StatusInfo(@Nullable S status, @Nullable Instant lastUpdated) {
            this.status = status;
            this.lastUpdated = lastUpdated;
        }

        /**
         * @param <S>
         * @return
         */
        public static <S> @NotNull StatusInfo<S> empty() {
            return new StatusInfo<>(null, null);
        }

        /**
         * @param status
         * @param lastUpdated
         * @param <S>
         * @return
         */
        public static <S> @NotNull StatusInfo<S> of(@NotNull S status, @NotNull Instant lastUpdated) {
            return new StatusInfo<>(requireNonNull(status), requireNonNull(lastUpdated));
        }

        /**
         * @return
         */
        public @NotNull S get() {
            if (status == null) {
                throw new IllegalStateException("No status information available");
            }
            return status;
        }

        /**
         * @return
         */
        public @NotNull Instant getLastUpdated() {
            if (lastUpdated == null) {
                throw new IllegalStateException("No status information available");
            }
            return lastUpdated;
        }

        /**
         * @return
         */
        public boolean isEmpty() {
            return status == null;
        }
    }
}
