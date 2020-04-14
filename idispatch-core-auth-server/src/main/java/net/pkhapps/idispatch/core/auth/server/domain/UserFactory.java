package net.pkhapps.idispatch.core.auth.server.domain;

import net.pkhapps.idispatch.core.server.support.domain.Factory;
import net.pkhapps.idispatch.core.server.support.security.TenantId;
import org.jetbrains.annotations.NotNull;

/**
 * TODO document me
 */
public class UserFactory implements Factory<User> {

    public @NotNull User newUser(@NotNull TenantId tenant,
                                 @NotNull String username,
                                 @NotNull String displayName) {
        throw new UnsupportedOperationException("not implemented");
    }
}
