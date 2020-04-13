package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface DomainEventSubscriber<EventT extends DomainEvent> {

    void onDomainEvent(@NotNull EventT domainEvent);
}
