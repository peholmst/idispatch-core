package net.pkhapps.idispatch.core.auth.server.domain;

import net.pkhapps.idispatch.core.server.support.domain.AggregateRoot;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me
 */
public abstract class Account extends AggregateRoot<AccountId> {

    public Account(@NotNull AccountId id) {
        super(id);
    }
}
