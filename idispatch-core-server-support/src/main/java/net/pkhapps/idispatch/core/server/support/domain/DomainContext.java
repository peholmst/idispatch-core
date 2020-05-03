package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

import java.time.Clock;
import java.time.Instant;

/**
 * Context interface for accessing all the parts of the domain model.
 *
 * @see #current()
 */
public interface DomainContext {

    /**
     * Returns the current domain context.
     *
     * @return the domain context
     * @see DomainContextHolder
     */
    static @NotNull DomainContext current() {
        return DomainContextHolder.getCurrent();
    }

    /**
     * Returns the clock that is used to get the current time.
     *
     * @return the clock
     */
    @NotNull Clock clock();

    /**
     * Returns the current time as returned by the {@linkplain #clock() context clock}.
     *
     * @return the current time
     */
    default @NotNull Instant now() {
        return clock().instant();
    }

    /**
     * Returns the domain event publisher for publishing and subscribing to domain events.
     *
     * @return the domain event publisher
     */
    @NotNull DomainEventPublisher domainEvents();

    /**
     * Looks up and returns the domain service of the given type. Instances returned by this method can be cached and
     * reused until the domain context is disposed of.
     *
     * @param serviceType the type of the domain service
     * @param <ServiceT>  the type of the domain service
     * @return the domain service singleton instance
     * @throws IllegalArgumentException if a domain service of the given type was not found
     */
    <ServiceT extends DomainService> @NotNull ServiceT service(@NotNull Class<ServiceT> serviceType);

    /**
     * Looks up and returns the repository of the given type. Instances returned by this method can be cached and
     * reused until the domain context is disposed of.
     *
     * @param repositoryType the type of the repository
     * @param <RepositoryT>  the type of the repository
     * @return the repository singleton instance
     * @throws IllegalArgumentException if a repository of the given type was not found
     */
    <RepositoryT extends Repository<?, ?>> @NotNull RepositoryT repository(
            @NotNull Class<RepositoryT> repositoryType);

    /**
     * Looks up and returns the factory of the given type. Instances returned by this method can be cached and reused
     * until the domain context is disposed of.
     *
     * @param factoryType the type of the factory
     * @param <FactoryT>  the type of the factory
     * @return the factory singleton instance
     * @throws IllegalArgumentException if a factory of the given type was not found
     */
    <FactoryT extends Factory<?>> @NotNull FactoryT factory(@NotNull Class<FactoryT> factoryType);
}
