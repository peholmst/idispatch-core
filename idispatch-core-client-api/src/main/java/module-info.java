module idispatch.core.client.api {
    requires org.locationtech.jts;

    requires static org.jetbrains.annotations;

    exports net.pkhapps.idispatch.core.client.api.alert;
    exports net.pkhapps.idispatch.core.client.api.assignment;
    exports net.pkhapps.idispatch.core.client.api.auth;
    exports net.pkhapps.idispatch.core.client.api.incident;
    exports net.pkhapps.idispatch.core.client.api.location;
    exports net.pkhapps.idispatch.core.client.api.org;
    exports net.pkhapps.idispatch.core.client.api.resource;
    exports net.pkhapps.idispatch.core.client.api.spi;
    exports net.pkhapps.idispatch.core.client.api.status;
    exports net.pkhapps.idispatch.core.client.api.util;

    uses net.pkhapps.idispatch.core.client.api.spi.CoreFactory;
}