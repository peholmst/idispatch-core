package net.pkhapps.idispatch.core.server.support.domain;

import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.LeafCreator;
import net.pkhapps.idispatch.core.server.support.domain.persistence.annotation.LeafValue;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * TODO Document me
 */
public abstract class UUIDDomainObjectId implements DomainObjectId {

    private final String uuid;

    public UUIDDomainObjectId(@NotNull UUID uuid) {
        this(uuid.toString());
    }

    @LeafCreator
    protected UUIDDomainObjectId(@NotNull String uuid) {
        this.uuid = Objects.requireNonNull(uuid);
    }

    public UUIDDomainObjectId() {
        this(UUID.randomUUID());
    }

    @Override
    @LeafValue
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UUIDDomainObjectId that = (UUIDDomainObjectId) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
