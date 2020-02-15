package net.pkhapps.idispatch.core.client.api.status;

import net.pkhapps.idispatch.core.client.api.util.Color;
import net.pkhapps.idispatch.core.client.api.util.IdentifiableObject;
import net.pkhapps.idispatch.core.client.api.util.MultilingualString;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A resource state is, as the name suggests, a state that a resource can be in at any given point in time. Resource
 * states are either assigned automatically by the system or manually by the dispatcher or the resource itself (through
 * some kind of status panel on the dashboard in the vehicle, for example). These states are of great importance to
 * both the system and to the human users of the system and that is why they also have colors, to make it easy to
 * quickly spot what state a certain resource is in.
 *
 * @see ResourceStatus
 */
public class ResourceState extends IdentifiableObject<ResourceStateId> implements Comparable<ResourceState> {

    private final MultilingualString name;
    private final Color color;
    private final boolean settableByDispatcher;
    private final boolean settableByResource;
    private final int order;
    private final Set<ResourceStateId> applicableTransitions;

    public ResourceState(@NotNull ResourceStateId id,
                         @NotNull MultilingualString name,
                         @NotNull Color color,
                         boolean settableByDispatcher,
                         boolean settableByResource,
                         int order,
                         @NotNull Set<ResourceStateId> applicableTransitions) {
        super(id);
        this.name = requireNonNull(name);
        this.color = requireNonNull(color);
        this.settableByDispatcher = settableByDispatcher;
        this.settableByResource = settableByResource;
        this.order = order;
        this.applicableTransitions = Set.copyOf(applicableTransitions);
    }

    /**
     * The name of the state.
     */
    public @NotNull MultilingualString getName() {
        return name;
    }

    /**
     * The color of the state (for user interfaces).
     */
    public @NotNull Color getColor() {
        return color;
    }

    /**
     * Whether this state can be set manually by the dispatcher.
     */
    public boolean isSettableByDispatcher() {
        return settableByDispatcher;
    }

    /**
     * Whether this state can be set manually by the resource itself.
     */
    public boolean isSettableByResource() {
        return settableByResource;
    }

    /**
     * All states that the resource can transition to from this state.
     */
    public @NotNull Set<ResourceStateId> getApplicableTransitions() {
        return applicableTransitions;
    }

    /**
     * Checks if the resource can transition to the given state from this state.
     *
     * @param resourceStateId the state to transition to
     * @return true if transition if allowed, false otherwise
     */
    public boolean canTransitionTo(@NotNull ResourceStateId resourceStateId) {
        return applicableTransitions.contains(resourceStateId);
    }

    @Override
    public int compareTo(@NotNull ResourceState o) {
        return order - o.order;
    }
}
