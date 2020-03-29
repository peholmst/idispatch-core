package net.pkhapps.idispatch.core.client.support.api;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * TODO Document me
 *
 * @param <T>
 * @param <ID>
 */
public interface HasLookupById<T, ID> {

    /**
     * @param id
     * @return
     */
    @NotNull Optional<T> findById(@NotNull ID id);
}
