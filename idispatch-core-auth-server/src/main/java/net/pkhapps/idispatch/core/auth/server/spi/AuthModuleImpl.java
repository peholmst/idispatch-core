package net.pkhapps.idispatch.core.auth.server.spi;

import net.pkhapps.idispatch.core.auth.server.AuthModule;
import net.pkhapps.idispatch.core.auth.server.application.AccountManagementService;
import net.pkhapps.idispatch.core.auth.server.application.AuthenticationService;
import net.pkhapps.idispatch.core.auth.server.application.PasswordService;
import net.pkhapps.idispatch.core.auth.server.application.TenantManagementService;
import org.jetbrains.annotations.NotNull;

public class AuthModuleImpl implements AuthModule {

    @Override
    public @NotNull AccountManagementService accountManagementService() {
        return null;
    }

    @Override
    public @NotNull AuthenticationService authenticationService() {
        return null;
    }

    @Override
    public @NotNull PasswordService passwordService() {
        return null;
    }

    @Override
    public @NotNull TenantManagementService tenantManagementService() {
        return null;
    }
}
