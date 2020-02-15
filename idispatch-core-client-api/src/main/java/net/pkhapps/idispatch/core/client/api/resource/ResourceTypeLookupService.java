package net.pkhapps.idispatch.core.client.api.resource;

import net.pkhapps.idispatch.core.client.api.util.LookupService;

/**
 * Service for looking up {@linkplain ResourceType resource types}.
 */
public interface ResourceTypeLookupService extends
        LookupService.FindAll<ResourceType, ResourceTypeId, Void>,
        LookupService.FindBySearchTerm<ResourceType, ResourceTypeId, Void> {
}
