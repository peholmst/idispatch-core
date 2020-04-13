module idispatch.core.server.support {
    requires static org.jetbrains.annotations;

    requires org.slf4j;

    exports net.pkhapps.idispatch.core.server.support.domain;
    exports net.pkhapps.idispatch.core.server.support.domain.persistence;
    exports net.pkhapps.idispatch.core.server.support.domain.persistence.annotation;
    exports net.pkhapps.idispatch.core.server.support.security;
    exports net.pkhapps.idispatch.core.server.support.spi;

    uses net.pkhapps.idispatch.core.server.support.spi.Module;
}