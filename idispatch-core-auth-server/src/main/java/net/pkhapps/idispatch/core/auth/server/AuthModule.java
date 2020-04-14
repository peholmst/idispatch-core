package net.pkhapps.idispatch.core.auth.server;

import net.pkhapps.idispatch.core.auth.server.application.AccountManagementService;
import net.pkhapps.idispatch.core.auth.server.application.AuthenticationService;
import net.pkhapps.idispatch.core.auth.server.application.PasswordService;
import net.pkhapps.idispatch.core.auth.server.application.TenantManagementService;
import net.pkhapps.idispatch.core.server.support.spi.Module;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me
 */
public interface AuthModule extends Module {

    @NotNull AccountManagementService accountManagementService();

    @NotNull AuthenticationService authenticationService();

    @NotNull PasswordService passwordService();

    @NotNull TenantManagementService tenantManagementService();
}
