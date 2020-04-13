package net.pkhapps.idispatch.core.server.support.security;

import org.jetbrains.annotations.NotNull;

import java.security.Principal;

/**
 * TODO document me
 */
public interface AuthenticatedPrincipal extends Principal {

    @NotNull TenantId tenantId();

    @NotNull String displayName();

    boolean hasAuthority(@NotNull GrantedAuthority authority);
}
