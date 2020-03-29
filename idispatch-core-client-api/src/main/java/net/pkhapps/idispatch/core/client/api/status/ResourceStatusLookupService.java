package net.pkhapps.idispatch.core.client.api.status;

import net.pkhapps.idispatch.core.client.api.org.OrganizationSearchHint;
import net.pkhapps.idispatch.core.client.api.resource.ResourceId;
import net.pkhapps.idispatch.core.client.api.util.LookupService;
import net.pkhapps.idispatch.core.client.api.util.Subscribable;

/**
 * Service for looking up {@linkplain ResourceStatus the status of resources}.
 */
public interface ResourceStatusLookupService extends
        LookupService.FindByGeometry<ResourceStatus, ResourceId, OrganizationSearchHint>,
        Subscribable.SubscribeByGeometry<ResourceId, OrganizationSearchHint> {
}
