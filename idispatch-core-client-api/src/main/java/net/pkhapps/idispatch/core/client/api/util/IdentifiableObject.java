package net.pkhapps.idispatch.core.client.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * TODO Document me
 *
 * @param <ID>
 */
public abstract class IdentifiableObject<ID extends ObjectId> {

    private final ID id;

    public IdentifiableObject(@NotNull ID id) {
        this.id = requireNonNull(id);
    }

    /**
     * The ID of this object. This is used by other parts of the system to refer to this particular object.
     */
    public @NotNull ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentifiableObject<?> that = (IdentifiableObject<?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
