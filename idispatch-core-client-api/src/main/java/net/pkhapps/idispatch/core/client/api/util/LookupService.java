package net.pkhapps.idispatch.core.client.api.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * TODO Document me
 *
 * @param <T>
 * @param <ID>
 */
public interface LookupService<T, ID extends ObjectId> {

    /**
     * @param id
     * @return
     */
    default @NotNull CompletableFuture<Optional<T>> findById(@NotNull ID id) {
        return findByIds(Set.of(id)).thenApply(result -> result.stream().findFirst());
    }

    /**
     * @param ids
     * @return
     */
    @NotNull CompletableFuture<List<T>> findByIds(@NotNull Iterable<ID> ids);

    /**
     * @param <T>
     * @param <ID>
     */
    interface FindAll<T, ID extends ObjectId, Hints> extends LookupService<T, ID> {

        /**
         * @return
         */
        default @NotNull CompletableFuture<List<T>> findAll() {
            return findAll(null);
        }

        /**
         * @param hints
         * @return
         */
        default @NotNull CompletableFuture<List<T>> findAll(@Nullable Hints hints) {
            return findAll(Integer.MAX_VALUE, hints);
        }

        /**
         * @param retrieveMax
         * @param hints
         * @return
         */
        @NotNull CompletableFuture<List<T>> findAll(int retrieveMax, @Nullable Hints hints);
    }

    /**
     * @param <T>
     * @param <ID>
     */
    interface FindBySearchTerm<T, ID extends ObjectId, Hints> extends LookupService<T, ID> {

        /**
         * @param searchTerm
         * @return
         */
        default @NotNull CompletableFuture<List<T>> findBySearchTerm(@NotNull String searchTerm) {
            return findBySearchTerm(searchTerm, null);
        }

        /**
         * @param searchTerm
         * @param hints
         * @return
         */
        default @NotNull CompletableFuture<List<T>> findBySearchTerm(@NotNull String searchTerm,
                                                                     @Nullable Hints hints) {
            return findBySearchTerm(searchTerm, Integer.MAX_VALUE, hints);
        }

        /**
         * @param searchTerm
         * @param retrieveMax
         * @param hints
         * @return
         */
        @NotNull CompletableFuture<List<T>> findBySearchTerm(@NotNull String searchTerm, int retrieveMax,
                                                             @Nullable Hints hints);
    }

    /**
     * @param <T>
     * @param <ID>
     */
    interface FindByGeometry<T, ID extends ObjectId, Hints> extends LookupService<T, ID> {

        /**
         * @param envelope
         * @param srid
         * @return
         */
        default @NotNull CompletableFuture<List<T>> findByGeometry(@NotNull Envelope envelope, int srid) {
            return findByGeometry(envelope, srid, null);
        }

        /**
         * @param envelope
         * @param srid
         * @return
         */
        default @NotNull CompletableFuture<List<T>> findByGeometry(@NotNull Envelope envelope, int srid,
                                                                   @Nullable Hints hints) {
            return findByGeometry(envelope, srid, Integer.MAX_VALUE, hints);
        }

        /**
         * @param envelope
         * @param srid
         * @param retrieveMax
         * @return
         */
        @NotNull CompletableFuture<List<T>> findByGeometry(@NotNull Envelope envelope, int srid, int retrieveMax,
                                                           @Nullable Hints hints);

        /**
         * @param center
         * @param radius
         * @param srid
         * @return
         */
        default @NotNull CompletableFuture<List<T>> findByGeometry(@NotNull Coordinate center, double radius,
                                                                   int srid) {
            return findByGeometry(center, radius, srid, null);
        }

        /**
         * @param center
         * @param radius
         * @param srid
         * @param hints
         * @return
         */
        default @NotNull CompletableFuture<List<T>> findByGeometry(@NotNull Coordinate center, double radius,
                                                                   int srid, @Nullable Hints hints) {
            return findByGeometry(center, radius, srid, Integer.MAX_VALUE, hints);
        }

        /**
         * @param center
         * @param radius
         * @param srid
         * @param retrieveMax
         * @param hints
         * @return
         */
        @NotNull CompletableFuture<List<T>> findByGeometry(@NotNull Coordinate center, double radius, int srid,
                                                           int retrieveMax, @Nullable Hints hints);
    }
}
