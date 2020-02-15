package net.pkhapps.idispatch.core.client.api.status;

import net.pkhapps.idispatch.core.client.api.resource.ResourceId;
import net.pkhapps.idispatch.core.client.api.util.LookupService;

/**
 * Service for looking up {@linkplain ResourceStatus the status of resources}.
 */
public interface ResourceStatusLookupService extends LookupService<ResourceStatus, ResourceId> {
}
