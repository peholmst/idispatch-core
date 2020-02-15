package net.pkhapps.idispatch.core.client.api.status;

import net.pkhapps.idispatch.core.client.api.util.LookupService;

/**
 * Service for looking up {@linkplain ResourceState resource states}.
 */
public interface ResourceStateLookupService extends LookupService.FindAll<ResourceState, ResourceStateId, Void> {
}
