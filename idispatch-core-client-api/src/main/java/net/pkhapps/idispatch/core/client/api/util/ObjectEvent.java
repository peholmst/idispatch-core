package net.pkhapps.idispatch.core.client.api.util;

import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * TODO Document me
 *
 * @param <ID>
 */
public abstract class ObjectEvent<ID extends ObjectId> {

    private final ID id;

    protected ObjectEvent(@NotNull ID id) {
        this.id = requireNonNull(id);
    }

    public @NotNull ID getId() {
        return id;
    }
}
