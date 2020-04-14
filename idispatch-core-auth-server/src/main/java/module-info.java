import net.pkhapps.idispatch.core.auth.server.spi.AuthModuleImpl;

module idispatch.core.auth.server {
    requires static org.jetbrains.annotations;

    requires idispatch.core.server.support;

    exports net.pkhapps.core.auth.server;
    exports net.pkhapps.core.auth.server.application;

    provides net.pkhapps.idispatch.core.server.support.spi.Module
            with AuthModuleImpl;
}