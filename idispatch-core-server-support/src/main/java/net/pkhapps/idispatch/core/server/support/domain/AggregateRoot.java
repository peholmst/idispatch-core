package net.pkhapps.idispatch.core.server.support.domain;

import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.SerializableAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * TODO Document me
 *
 * @param <IdT>
 */
public abstract class AggregateRoot<IdT extends DomainObjectId> extends Entity<IdT> {

    @SerializableAttribute(name = "_version")
    private long version = 0;

    public AggregateRoot(@NotNull IdT id) {
        super(id);
    }

    /**
     * @param domainEvent
     */
    protected void registerDomainEvent(@NotNull DomainEvent domainEvent) {
        // TODO Implement me
    }

    /**
     * @return
     */
    @NotNull Collection<DomainEvent> domainEvents() {
        return Collections.emptyList();
    }

    /**
     *
     */
    void clearDomainEvents() {

    }

    /**
     * @return
     */
    public boolean isPersistent() {
        return version > 0;
    }

    /**
     * @return
     */
    public long version() {
        return version;
    }
}
