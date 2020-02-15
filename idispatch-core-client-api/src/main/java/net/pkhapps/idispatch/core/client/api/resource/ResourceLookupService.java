package net.pkhapps.idispatch.core.client.api.resource;

import net.pkhapps.idispatch.core.client.api.org.OrganizationSearchHint;
import net.pkhapps.idispatch.core.client.api.util.LookupService;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Service for looking up {@linkplain Resource resources}.
 */
public interface ResourceLookupService extends
        LookupService.FindAll<Resource, ResourceId, OrganizationSearchHint>,
        LookupService.FindBySearchTerm<Resource, ResourceId, OrganizationSearchHint> {

    /**
     * Finds the resource with the given call sign.
     *
     * @param callSign the call sign to search for
     * @return a {@code CompletableFuture} returning the resource or an empty {@code Optional} if not found
     */
    default @NotNull CompletableFuture<Optional<Resource>> findByCallSign(@NotNull String callSign) {
        return findByCallSigns(Set.of(callSign)).thenApply(result -> result.stream().findFirst());
    }

    /**
     * Finds the resources with the given call signs.
     *
     * @param callSigns the call signs to search for
     * @return a {@code CompletableFuture} returning the resources that were found
     */
    @NotNull CompletableFuture<List<Resource>> findByCallSigns(@NotNull Iterable<String> callSigns);
}
