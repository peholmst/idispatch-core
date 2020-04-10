module idispatch.core.client.support.grpc.testing {
    requires static org.jetbrains.annotations;

    requires io.grpc;
    requires junit;
    requires org.assertj.core;

    exports net.pkhapps.idispatch.core.client.support.grpc.testing;
}