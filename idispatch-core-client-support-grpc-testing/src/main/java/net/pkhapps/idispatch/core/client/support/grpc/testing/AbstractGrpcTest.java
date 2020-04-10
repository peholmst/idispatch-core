package net.pkhapps.idispatch.core.client.support.grpc.testing;

import io.grpc.*;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * TODO Document me
 */
@RunWith(JUnit4.class)
public abstract class AbstractGrpcTest {

    @Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();
    private final Context.Key<Metadata> HEADERS = Context.key("headers");

    @Before
    public void setUp() throws Exception {
        var serverName = InProcessServerBuilder.generateName();
        var serverBuilder = InProcessServerBuilder.forName(serverName).directExecutor();
        System.out.println("Setting up server side");
        setUpServerSide().forEach(service -> serverBuilder.addService(ServerInterceptors.intercept(service,
                new HeadersInterceptor())));
        var server = serverBuilder.build();
        System.out.println("Starting server");
        grpcCleanupRule.register(server.start());
        var channel = grpcCleanupRule.register(
                InProcessChannelBuilder.forName(serverName)
                        .directExecutor()
                        .build()
        );
        System.out.println("Setting up client side");
        setUpClientSide(channel);
        System.out.println("Running test");
    }

    protected abstract @NotNull Stream<BindableService> setUpServerSide() throws Exception;

    protected abstract void setUpClientSide(@NotNull Channel channel) throws Exception;

    protected @NotNull Metadata getHeaders() {
        return requireNonNull(HEADERS.get());
    }

    private class HeadersInterceptor implements ServerInterceptor {

        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
            var context = Context.current().withValue(HEADERS, headers);
            return Contexts.interceptCall(context, call, headers, next);
        }
    }
}
