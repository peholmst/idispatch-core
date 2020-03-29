package net.pkhapps.idispatch.core.client.support.api;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * TODO document me
 *
 * @param <T>
 * @param <F>
 */
public interface HasListing<T, F> {

    /**
     * @param filter
     * @return
     */
    long count(@NotNull F filter);

    /**
     * @param filter
     * @param offset
     * @param limit
     * @return
     */
    @NotNull Stream<T> list(@NotNull F filter, long offset, int limit);
}
