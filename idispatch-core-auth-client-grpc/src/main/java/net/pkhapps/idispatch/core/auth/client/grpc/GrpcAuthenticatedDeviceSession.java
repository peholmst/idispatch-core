package net.pkhapps.idispatch.core.auth.client.grpc;

import com.google.protobuf.Empty;
import net.pkhapps.idispatch.core.auth.client.api.AuthenticatedPrincipal;
import net.pkhapps.idispatch.core.auth.client.api.AuthenticatedSession;
import net.pkhapps.idispatch.core.auth.client.api.CannotRefreshTokenException;
import net.pkhapps.idispatch.core.auth.client.api.InternalAuthenticationException;
import net.pkhapps.idispatch.core.auth.proto.AuthenticationServiceGrpc;
import net.pkhapps.idispatch.core.auth.proto.DeviceAuthenticationOutcome;
import net.pkhapps.idispatch.core.client.support.grpc.AuthenticationToken;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.Subject;
import java.security.PrivilegedAction;
import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link AuthenticatedSession} that represents the session of an authenticated device.
 */
final class GrpcAuthenticatedDeviceSession implements AuthenticatedSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcAuthenticatedDeviceSession.class);

    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub server;
    private final GrpcAuthenticatedDevice principal;
    private final Subject subject;
    private AuthenticationToken token;
    private Instant expirationTime;

    GrpcAuthenticatedDeviceSession(@NotNull AuthenticationServiceGrpc.AuthenticationServiceBlockingStub server,
                                   @NotNull DeviceAuthenticationOutcome outcome) {
        this.server = requireNonNull(server);
        if (!outcome.hasPrincipal()) {
            throw new InternalAuthenticationException("Authentication outcome has no principal");
        }
        if (!outcome.hasToken()) {
            throw new InternalAuthenticationException("Authentication outcome has no token");
        }
        this.principal = new GrpcAuthenticatedDevice(outcome.getPrincipal());
        this.token = new AuthenticationTokenImpl(outcome.getToken());
        this.subject = new Subject();
        this.subject.getPrincipals().add(principal);
        this.subject.getPrivateCredentials().add(token);
        this.expirationTime = token.validTo();
        LOGGER.debug("Creating new session for device [{}] and tenant [{}]", principal.getName(), principal.tenantId());
    }

    @Override
    public @NotNull Subject subject() {
        return subject;
    }

    @Override
    public @NotNull AuthenticatedPrincipal principal() {
        return principal;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void invalidate() {
        Subject.doAs(subject, (PrivilegedAction<Object>) () -> {
            server.invalidateToken(Empty.getDefaultInstance());
            return null;
        });
        subject.getPrivateCredentials().remove(token);
        this.token = null;
    }

    @Override
    public @NotNull Instant expiresOn() {
        return expirationTime;
    }

    @Override
    public void refresh() {
        Subject.doAs(subject, (PrivilegedAction<Object>) () -> {
            var response = server.refreshToken(Empty.getDefaultInstance());
            if (response.getSuccessful() && response.hasToken()) {
                subject.getPrivateCredentials().remove(token);
                token = new AuthenticationTokenImpl(response.getToken());
                subject.getPrivateCredentials().add(token);
                expirationTime = token.validTo();
                return null;
            } else {
                throw new CannotRefreshTokenException();
            }
        });
    }

    @Override
    public boolean isValid() {
        var now = Instant.now();
        return token != null && now.compareTo(token.validFrom()) >= 0 && now.isBefore(token.validTo());
    }
}
