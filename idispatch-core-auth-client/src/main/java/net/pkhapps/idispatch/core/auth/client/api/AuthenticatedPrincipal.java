package net.pkhapps.idispatch.core.auth.client.api;

import org.jetbrains.annotations.NotNull;

import java.security.Principal;

/**
 * Interface defining the principal of an iDispatch user, typically a human user or a device.
 */
public interface AuthenticatedPrincipal extends Principal {

    /**
     * The display name of the principal. This is what should be shown in UIs. It is not necessarily the same
     * as the {@linkplain #getName() principal name}, which can be used to uniquely identify the principal.
     */
    @NotNull String displayName();

    /**
     * Checks if this principal has the given authority.
     *
     * @param authority the name of the authority to check
     * @return true if the principal has the authority, false if not
     */
    boolean hasAuthority(@NotNull String authority);
}
