module idispatch.core.server.support.mongodb {
    requires static org.jetbrains.annotations;

    requires idispatch.core.server.support;
    requires mongo.java.driver;
    requires org.slf4j;

    exports net.pkhapps.idispatch.core.server.support.mongodb.domain;
    opens net.pkhapps.idispatch.core.server.support.mongodb.domain to idispatch.core.server.support;
}