package net.pkhapps.idispatch.core.client.api.assignment;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Service for looking up {@linkplain AssignmentType assignment types}.
 */
public interface AssignmentTypeLookupService {

    /**
     * Finds the assignment type with the given {@linkplain AssignmentType#getCode() code}.
     *
     * @param code the code to search for
     * @return a {@code CompletableFuture} returning the assignment type or an empty {@code Optional} if not found
     */
    @NotNull CompletableFuture<Optional<AssignmentType>> findByCode(@NotNull String code);

    /**
     * Finds the assignment type with the given {@linkplain AssignmentType#getId() id}.
     *
     * @param id the ID to search for
     * @return a {@code CompletableFuture} returning the assignment type or an empty {@code Optional} if not found
     */
    @NotNull CompletableFuture<Optional<AssignmentType>> findById(@NotNull AssignmentTypeId id);

    /**
     * Finds all assignment types.
     *
     * @return a {@code CompletableFuture} returning the assignment types
     */
    @NotNull CompletableFuture<List<AssignmentType>> findAll();
}
