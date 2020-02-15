package net.pkhapps.idispatch.core.client.api.spi;

import net.pkhapps.idispatch.core.client.api.assignment.AssignmentTypeLookupService;
import net.pkhapps.idispatch.core.client.api.auth.AuthenticationService;
import net.pkhapps.idispatch.core.client.api.resource.ResourceLookupService;
import net.pkhapps.idispatch.core.client.api.resource.ResourceTypeLookupService;
import net.pkhapps.idispatch.core.client.api.resource.StationLookupService;
import net.pkhapps.idispatch.core.client.api.status.ResourceStateLookupService;
import net.pkhapps.idispatch.core.client.api.status.ResourceStatusLookupService;
import org.jetbrains.annotations.NotNull;

/**
 * Provides access to the iDispatch Core Client services.
 *
 * @see CoreFactory
 */
public interface Core {

    /**
     * The {@link AuthenticationService} for authenticating users of iDispatch Core
     */
    @NotNull AuthenticationService getAuthenticationService();

    /**
     * The {@link AssignmentTypeLookupService} for looking up assignment types.
     */
    @NotNull AssignmentTypeLookupService getAssignmentTypeLookupService();

    /**
     * The {@link ResourceLookupService} for looking up resources (without taking status and current location into
     * account).
     */
    @NotNull ResourceLookupService getResourceLookupService();

    /**
     * The {@link ResourceStateLookupService} for looking up resource states.
     */
    @NotNull ResourceStateLookupService getResourceStateLookupService();

    /**
     * The {@link ResourceStatusLookupService} for looking up the status of resources.
     */
    @NotNull ResourceStatusLookupService getResourceStatusLookupService();

    /**
     * The {@link ResourceTypeLookupService} for looking up resource types.
     */
    @NotNull ResourceTypeLookupService getResourceTypeLookupService();

    /**
     * The {@link StationLookupService} for looking up stations.
     */
    @NotNull StationLookupService getStationLookupService();

    // TODO Define services
}
