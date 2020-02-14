module idispatch.core.client.api {
    requires static org.jetbrains.annotations;

    exports net.pkhapps.idispatch.core.client.api.auth;
    exports net.pkhapps.idispatch.core.client.api.org;
    exports net.pkhapps.idispatch.core.client.api.spi;
    exports net.pkhapps.idispatch.core.client.api.util;

    uses net.pkhapps.idispatch.core.client.api.spi.CoreFactory;
}