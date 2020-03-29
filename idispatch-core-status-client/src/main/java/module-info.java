module idispatch.core.status.client.api {
    uses net.pkhapps.idispatch.core.status.client.api.spi.StatusClientFactory;

    requires static org.jetbrains.annotations;

    requires idispatch.core.client.support.api;
    requires org.locationtech.jts;

    exports net.pkhapps.idispatch.core.status.client.api;
    exports net.pkhapps.idispatch.core.status.client.api.spi;
}