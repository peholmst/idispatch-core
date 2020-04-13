package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * TODO Document me
 */
public abstract class AbstractRepository<AggregateRootT extends AggregateRoot<IdT>, IdT extends DomainObjectId>
        implements Repository<AggregateRootT, IdT> {

    /**
     * @param aggregate
     * @return
     */
    protected @NotNull Collection<DomainEvent> getDomainEvents(@NotNull AggregateRootT aggregate) {
        return aggregate.domainEvents();
    }

    /**
     * @param aggregate
     */
    protected void clearDomainEvents(@NotNull AggregateRootT aggregate) {
        aggregate.clearDomainEvents();
    }
}
