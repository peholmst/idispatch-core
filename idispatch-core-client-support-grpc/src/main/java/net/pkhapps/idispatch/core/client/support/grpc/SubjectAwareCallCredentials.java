package net.pkhapps.idispatch.core.client.support.grpc;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.Subject;
import java.security.AccessController;
import java.util.Optional;
import java.util.concurrent.Executor;

/**
 * TODO Document me
 */
public class SubjectAwareCallCredentials extends CallCredentials {

    private static final CallCredentials INSTANCE = new SubjectAwareCallCredentials();
    private static final Metadata.Key<byte[]> AUTH_TOKEN_KEY =
            Metadata.Key.of("x-idispatch-auth-token" + Metadata.BINARY_HEADER_SUFFIX,
                    Metadata.BINARY_BYTE_MARSHALLER);

    private SubjectAwareCallCredentials() {
    }

    public static @NotNull CallCredentials getInstance() {
        return INSTANCE;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        var headers = new Metadata();
        Optional.ofNullable(Subject.getSubject(AccessController.getContext()))
                .flatMap(subject -> subject.getPrivateCredentials(AuthenticationToken.class).stream().findAny())
                .ifPresent(token -> token.addToHeaders(AUTH_TOKEN_KEY, headers));
        applier.apply(headers);
    }

    @Override
    public void thisUsesUnstableApi() {
        // NOP
    }
}
