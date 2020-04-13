package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * TODO Document me
 */
public interface DomainEventPublisher {

    void publish(@NotNull DomainEvent domainEvent);

    void publishAll(@NotNull Collection<DomainEvent> domainEvents);

    <EventT extends DomainEvent> @NotNull DomainEventSubscription subscribe(@NotNull Class<EventT> domainEventType,
                                                                            @NotNull DomainEventSubscriber<EventT> subscriber);
}
