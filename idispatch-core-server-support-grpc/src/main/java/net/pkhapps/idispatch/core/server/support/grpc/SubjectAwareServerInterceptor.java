package net.pkhapps.idispatch.core.server.support.grpc;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.Subject;
import java.security.PrivilegedAction;

import static java.util.Objects.requireNonNull;

/**
 * TODO Document me
 */
public class SubjectAwareServerInterceptor implements ServerInterceptor {

    private static final Metadata.Key<byte[]> AUTH_TOKEN_KEY =
            Metadata.Key.of("x-idispatch-auth-token" + Metadata.BINARY_HEADER_SUFFIX,
                    Metadata.BINARY_BYTE_MARSHALLER);

    private final TokenRegistry tokenRegistry;

    /**
     * @param tokenRegistry
     */
    public SubjectAwareServerInterceptor(@NotNull TokenRegistry tokenRegistry) {
        this.tokenRegistry = requireNonNull(tokenRegistry);
    }

    @Override
    public <ReqT, RespT>
    ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        var token = headers.get(AUTH_TOKEN_KEY);
        if (token != null) {
            var principal = tokenRegistry.getPrincipalOfToken(token);
            var subject = new Subject();
            subject.getPrincipals().add(principal);
            // TODO This may not be the right way of doing things.
            return Subject.doAs(subject, (PrivilegedAction<ServerCall.Listener<ReqT>>) () -> next.startCall(call, headers));
        }
        return next.startCall(call, headers);
    }
}
