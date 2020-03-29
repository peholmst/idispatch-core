module idispatch.core.auth.client.api {
    uses net.pkhapps.idispatch.core.auth.client.api.spi.AuthenticationClientFactory;

    requires static org.jetbrains.annotations;

    requires idispatch.core.client.support.api;

    exports net.pkhapps.idispatch.core.auth.client.api;
    exports net.pkhapps.idispatch.core.auth.client.api.device;
    exports net.pkhapps.idispatch.core.auth.client.api.user;
}