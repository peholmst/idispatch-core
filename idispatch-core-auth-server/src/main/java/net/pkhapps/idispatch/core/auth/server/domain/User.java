package net.pkhapps.idispatch.core.auth.server.domain;

import org.jetbrains.annotations.NotNull;

public class User extends Account {

    public User(@NotNull AccountId id) {
        super(id);
    }
}
