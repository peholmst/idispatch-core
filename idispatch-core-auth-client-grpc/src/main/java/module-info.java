module idispatch.core.auth.client.grpc {
    requires static org.jetbrains.annotations;

    requires com.google.protobuf;
    requires idispatch.core.auth.client.api;
    requires idispatch.core.auth.protobuf;
    requires idispatch.core.client.support.api;
    requires idispatch.core.client.support.grpc;
    requires io.grpc;
    requires org.slf4j;

    provides net.pkhapps.idispatch.core.auth.client.api.spi.AuthenticationClientFactory
            with net.pkhapps.idispatch.core.auth.client.grpc.spi.GrpcAuthenticationClientFactory;
}
