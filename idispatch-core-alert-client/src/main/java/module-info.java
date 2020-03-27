module idispatch.core.alert.client.api {
    uses net.pkhapps.idispatch.core.alert.client.api.spi.AlertClientFactory;

    requires static org.jetbrains.annotations;

    requires idispatch.core.client.support;
    requires org.locationtech.jts;

    exports net.pkhapps.idispatch.core.alert.client.api;
    exports net.pkhapps.idispatch.core.alert.client.api.spi;
}