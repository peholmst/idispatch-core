package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * TODO Document me
 *
 * @param <AggregateRootT>
 * @param <IdT>
 */
public interface Repository<AggregateRootT extends AggregateRoot<IdT>, IdT extends DomainObjectId> {

    @NotNull Optional<AggregateRootT> findById(@NotNull IdT id);

    default @NotNull AggregateRootT getById(@NotNull IdT id) {
        return findById(id).orElseThrow(AggregateRootNotFoundException::new);
    }

    @NotNull AggregateRootT save(@NotNull AggregateRootT aggregate);

    default void delete(@NotNull AggregateRootT aggregate) {
        deleteById(aggregate.id());
    }

    void deleteById(@NotNull IdT id);
}
