module idispatch.core.auth.protobuf {
    requires static java.annotation;

    requires com.google.common;
    requires com.google.protobuf;
    requires grpc.protobuf;
    requires io.grpc; // TODO replace with real gRPC modules once they support JPMS

    exports net.pkhapps.idispatch.core.auth.proto;
}