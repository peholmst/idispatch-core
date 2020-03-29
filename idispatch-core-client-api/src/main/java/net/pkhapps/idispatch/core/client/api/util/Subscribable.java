package net.pkhapps.idispatch.core.client.api.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.locationtech.jts.geom.Envelope;

import java.util.Set;

/**
 * TODO Document me
 *
 * @param <ID>
 */
public interface Subscribable<ID extends ObjectId> {

    /**
     * @param subscriber
     * @param eventType
     * @param id
     * @param <E>
     * @return
     */
    default <E extends ObjectEvent<ID>> @NotNull Subscription subscribe(@NotNull Subscriber<ID, E> subscriber,
                                                                        @NotNull Class<E> eventType, @NotNull ID id) {
        return subscribe(subscriber, eventType, Set.of(id));
    }

    /**
     * @param subscriber
     * @param eventType
     * @param ids
     * @param <E>
     * @return
     */
    <E extends ObjectEvent<ID>> @NotNull Subscription subscribe(@NotNull Subscriber<ID, E> subscriber,
                                                                @NotNull Class<E> eventType, @NotNull Iterable<ID> ids);

    /**
     * @param <ID>
     * @param <E>
     */
    @FunctionalInterface
    interface Subscriber<ID extends ObjectId, E extends ObjectEvent<ID>> {

        /**
         * @param events
         */
        void handleEvents(@NotNull Iterable<E> events);
    }

    /**
     *
     */
    @FunctionalInterface
    interface Subscription {
        /**
         *
         */
        void unsubscribe();
    }

    /**
     * @param <ID>
     * @param <Hints>
     */
    interface SubscribeToAll<ID extends ObjectId, Hints> extends Subscribable<ID> {

        /**
         * @param subscriber
         * @param eventType
         * @param <E>
         * @return
         */
        default <E extends ObjectEvent<ID>> @NotNull Subscription subscribeToAll(@NotNull Subscriber<ID, E> subscriber,
                                                                                 @NotNull Class<E> eventType) {
            return subscribeToAll(subscriber, eventType, null);
        }

        /**
         * @param subscriber
         * @param eventType
         * @param hints
         * @param <E>
         * @return
         */
        <E extends ObjectEvent<ID>> @NotNull Subscription subscribeToAll(@NotNull Subscriber<ID, E> subscriber,
                                                                         @NotNull Class<E> eventType,
                                                                         @Nullable Hints hints);
    }

    /**
     * @param <ID>
     * @param <Hints>
     */
    interface SubscribeByGeometry<ID extends ObjectId, Hints> extends Subscribable<ID> {

        /**
         * @param subscriber
         * @param eventType
         * @param envelope
         * @param srid
         * @param <E>
         * @return
         */
        default <E extends ObjectEvent<ID>> @NotNull Subscription subscribeByBounds(@NotNull Subscriber<ID, E> subscriber,
                                                                                    @NotNull Class<E> eventType,
                                                                                    @NotNull Envelope envelope,
                                                                                    int srid) {
            return subscribeByBounds(subscriber, eventType, envelope, srid, null);
        }

        /**
         * @param subscriber
         * @param eventType
         * @param envelope
         * @param srid
         * @param hints
         * @param <E>
         * @return
         */
        <E extends ObjectEvent<ID>> @NotNull Subscription subscribeByBounds(@NotNull Subscriber<ID, E> subscriber,
                                                                            @NotNull Class<E> eventType,
                                                                            @NotNull Envelope envelope,
                                                                            int srid,
                                                                            @Nullable Hints hints);
    }
}
