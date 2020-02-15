package net.pkhapps.idispatch.core.client.api.org;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

/**
 * TODO Document me
 */
@FunctionalInterface
public interface OrganizationSearchHint {

    /**
     * @return
     */
    static @NotNull OrganizationSearchHint includeResultsForAllOrganizations() {
        return Collections::emptySet;
    }

    /**
     * @param organizations
     * @return
     */
    static @NotNull OrganizationSearchHint includeResultsForOrganizations(@NotNull Set<OrganizationId> organizations) {
        return () -> organizations;
    }

    /**
     * @return
     */
    @NotNull Set<OrganizationId> getOrganizations();
}
