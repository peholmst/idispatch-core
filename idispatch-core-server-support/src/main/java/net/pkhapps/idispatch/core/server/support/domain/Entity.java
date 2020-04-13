package net.pkhapps.idispatch.core.server.support.domain;

import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.SerializableAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * TODO Document me
 *
 * @param <IdT>
 */
public abstract class Entity<IdT extends DomainObjectId> implements IdentifiableDomainObject<IdT> {

    @SerializableAttribute(name = "_id")
    private IdT id;

    public Entity(@NotNull IdT id) {
        this.id = requireNonNull(id);
    }

    @Override
    public @NotNull IdT id() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s{%s}", getClass().getSimpleName(), id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
