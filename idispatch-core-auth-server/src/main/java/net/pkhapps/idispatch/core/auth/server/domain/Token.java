package net.pkhapps.idispatch.core.auth.server.domain;

import net.pkhapps.idispatch.core.server.support.domain.AggregateRoot;
import org.jetbrains.annotations.NotNull;

public class Token extends AggregateRoot<TokenId> {

    public Token(@NotNull TokenId id) {
        super(id);
    }
}
