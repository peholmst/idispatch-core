package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

/**
 * Functional interface for domain event subscribers that are notified when new {@linkplain DomainEvent domain events}
 * are published.
 *
 * @param <EventT> the type of the domain event to receive
 * @see DomainEventPublisher#subscribe(Class, DomainEventSubscriber)
 */
@FunctionalInterface
public interface DomainEventSubscriber<EventT extends DomainEvent> {

    /**
     * Called when a domain event has been received. The subscriber must remember to
     * {@linkplain DomainEventEnvelope#acknowledge() acknowledge} the domain event to avoid receiving the event
     * again some time in the future. That said, the subscriber should still be prepared to handle the same event
     * more than once in case of system errors (in other words, the subscribers must be idempotent).
     *
     * @param domainEvent an envelope containing the domain event
     * @see DomainEventEnvelope
     */
    void onDomainEvent(@NotNull DomainEventEnvelope<EventT> domainEvent);
}
