package net.pkhapps.idispatch.core.client.api.status;

import net.pkhapps.idispatch.core.client.api.resource.ResourceId;
import net.pkhapps.idispatch.core.client.api.util.ObjectEvent;
import org.jetbrains.annotations.NotNull;

/**
 * This event is fired whenever the {@linkplain ResourceStatus status of a resource} is updated in any way. The event
 * contains the status object as it was then the event was originally published.
 */
public class ResourceStatusUpdatedEvent extends ObjectEvent<ResourceId> {

    private final ResourceStatus resourceStatus;

    public ResourceStatusUpdatedEvent(@NotNull ResourceStatus resourceStatus) {
        super(resourceStatus.getId());
        this.resourceStatus = resourceStatus;
    }

    public @NotNull ResourceStatus getResourceStatus() {
        return resourceStatus;
    }
}
