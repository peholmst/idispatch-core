module idispatch.core.client.support.grpc {
    requires static org.jetbrains.annotations;

    requires idispatch.core.client.support.api;
    requires io.grpc;

    exports net.pkhapps.idispatch.core.client.support.grpc;
}