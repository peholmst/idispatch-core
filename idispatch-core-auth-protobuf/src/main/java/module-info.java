module idispatch.core.auth.protobuf {
    requires java.annotation;

    requires com.google.common;
    requires grpc.api;
    requires grpc.stub;
    requires grpc.protobuf;
    requires protobuf.java;

    exports net.pkhapps.idispatch.core.auth.proto;
}