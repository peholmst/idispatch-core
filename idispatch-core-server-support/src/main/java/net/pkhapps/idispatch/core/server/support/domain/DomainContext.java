package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

import java.time.Clock;
import java.time.Instant;

/**
 * TODO Document me
 */
public abstract class DomainContext {

    public static @NotNull DomainContext current() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public abstract @NotNull Clock clock();

    public @NotNull Instant now() {
        return clock().instant();
    }

    public abstract @NotNull DomainEventPublisher domainEvents();

    public abstract <ServiceT extends DomainService> @NotNull ServiceT service(@NotNull Class<ServiceT> serviceType);

    public abstract <RepositoryT extends Repository<?, ?>> @NotNull RepositoryT repository(
            @NotNull Class<RepositoryT> repositoryType);

    public abstract <FactoryT extends Factory<?>> @NotNull FactoryT factory(@NotNull Class<FactoryT> factoryType);

    public abstract @NotNull UnitOfWorkManager unitOfWorkManager();
}
