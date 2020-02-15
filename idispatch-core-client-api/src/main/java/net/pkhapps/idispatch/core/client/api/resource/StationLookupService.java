package net.pkhapps.idispatch.core.client.api.resource;

import net.pkhapps.idispatch.core.client.api.org.OrganizationSearchHint;
import net.pkhapps.idispatch.core.client.api.util.LookupService;

/**
 * Service for looking up {@linkplain Station stations}.
 */
public interface StationLookupService extends
        LookupService.FindAll<Station, StationId, OrganizationSearchHint>,
        LookupService.FindBySearchTerm<Station, StationId, OrganizationSearchHint>,
        LookupService.FindByGeometry<Station, StationId, OrganizationSearchHint> {
}
