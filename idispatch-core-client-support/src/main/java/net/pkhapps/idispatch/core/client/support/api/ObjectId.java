package net.pkhapps.idispatch.core.client.support.api;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Value object used to identify an object of some kind. The value object can wrap any {@link Serializable} identifier,
 * such as strings, UUIDs or longs.
 */
public class ObjectId {

    private final Serializable objectId;

    /**
     * Creates a new, unique object ID.
     */
    public ObjectId() {
        this(UUID.randomUUID());
    }

    /**
     * Creates a new object ID that wraps the given ID.
     *
     * @param objectId the ID to wrap (such as a string, a UUID, a long)
     */
    public ObjectId(@NotNull Serializable objectId) {
        this.objectId = objectId;
    }

    /**
     * Unwraps the object ID.
     *
     * @return the underlying ID
     */
    public @NotNull Serializable unwrap() {
        return objectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectId objectId1 = (ObjectId) o;
        return objectId.equals(objectId1.objectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId);
    }

    @Override
    public String toString() {
        return objectId.toString();
    }
}
