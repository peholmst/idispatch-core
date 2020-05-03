package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Domain infrastructure service for publishing and subscribing to {@linkplain DomainEvent domain events}.
 *
 * @see AggregateRoot#registerDomainEvent(DomainEvent)
 * @see DomainContext#domainEvents()
 */
public interface DomainEventPublisher {

    /**
     * Publishes the given domain event. The event is guaranteed to eventually be delivered to all subscribers at least
     * once.
     *
     * @param domainEvent the domain event to publish
     */
    void publish(@NotNull DomainEvent domainEvent);

    /**
     * Publishes the given domain events. The events are guaranteed to eventually be delivered to all subscribers at
     * least one.
     *
     * @param domainEvents the domain events to publish
     */
    void publishAll(@NotNull Collection<DomainEvent> domainEvents);

    /**
     * Subscribes the given subscriber to be notified of events of the given type. The subscriber is guaranteed to
     * be notified at least once of all events published after the subscription until the subscription is cancelled.
     * Please note that subscribers should be idempotent, meaning they should support receiving the same event more
     * than once without causing unwanted side effects.
     *
     * @param domainEventType the type of events to receive. The subscriber will be notified of all events of this class
     *                        or any of its subclasses.
     * @param subscriber      the subscriber itself
     * @param <EventT>        the type of events to receive
     * @return a subscription handle that can be used to cancel the subscription
     * @see DomainEventSubscriber
     */
    <EventT extends DomainEvent>
    @NotNull DomainEventSubscription subscribe(@NotNull Class<EventT> domainEventType,
                                               @NotNull DomainEventSubscriber<? super EventT> subscriber);
}
