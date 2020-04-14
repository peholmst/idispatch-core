module idispatch.core.server.support.grpc {
    requires static org.jetbrains.annotations;

    requires idispatch.core.server.support;
    requires io.grpc;
    requires org.slf4j;

    exports net.pkhapps.idispatch.core.server.support.grpc;
    exports net.pkhapps.idispatch.core.server.support.grpc.spi;
}