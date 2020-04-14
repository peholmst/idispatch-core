package net.pkhapps.idispatch.core.auth.server.domain;

import net.pkhapps.idispatch.core.server.support.domain.AggregateRoot;
import net.pkhapps.idispatch.core.server.support.security.TenantId;
import org.jetbrains.annotations.NotNull;

public class Tenant extends AggregateRoot<TenantId> {

    public Tenant(@NotNull TenantId id) {
        super(id);
    }
}
