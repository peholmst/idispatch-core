module idispatch.core.server.app {
    requires io.grpc;
    requires idispatch.core.server.support;
    requires idispatch.core.server.support.grpc;
    requires org.slf4j;

    uses net.pkhapps.idispatch.core.server.support.grpc.spi.GrpcModule;
}