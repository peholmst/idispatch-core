module idispatch.core.auth.server.grpc {
    requires static org.jetbrains.annotations;

    requires com.google.protobuf;
    requires idispatch.core.auth.protobuf;
    requires idispatch.core.auth.server;
    requires idispatch.core.server.support;
    requires idispatch.core.server.support.grpc;
    requires io.grpc;
    requires org.slf4j;

    provides net.pkhapps.idispatch.core.server.support.grpc.spi.GrpcModule
            with net.pkhapps.idispatch.core.auth.server.grpc.spi.AuthServerGrpcModule;
}