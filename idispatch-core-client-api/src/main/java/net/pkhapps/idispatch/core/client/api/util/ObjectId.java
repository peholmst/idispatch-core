package net.pkhapps.idispatch.core.client.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * TODO Document me
 */
public abstract class ObjectId {

    private final String uuid;

    public ObjectId(@NotNull String uuid) {
        this.uuid = requireNonNull(uuid);
    }

    @Override
    public String toString() {
        return String.format("%s{uuid='%s'}", getClass().getSimpleName(), uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectId objectId = (ObjectId) o;
        return uuid.equals(objectId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
