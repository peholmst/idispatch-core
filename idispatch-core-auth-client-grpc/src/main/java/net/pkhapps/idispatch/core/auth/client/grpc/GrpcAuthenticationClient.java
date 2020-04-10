package net.pkhapps.idispatch.core.auth.client.grpc;

import net.pkhapps.idispatch.core.auth.client.api.AuthenticatedSession;
import net.pkhapps.idispatch.core.auth.client.api.AuthenticationClient;
import net.pkhapps.idispatch.core.auth.client.api.AuthenticationProcess;
import net.pkhapps.idispatch.core.auth.client.api.AuthenticationRequest;
import net.pkhapps.idispatch.core.auth.client.api.device.DeviceAuthenticationRequest;
import net.pkhapps.idispatch.core.auth.client.api.user.UserAuthenticationRequest;
import net.pkhapps.idispatch.core.auth.proto.AuthenticationServiceGrpc;
import net.pkhapps.idispatch.core.client.support.grpc.GrpcContext;
import net.pkhapps.idispatch.core.client.support.grpc.GrpcServiceClient;
import net.pkhapps.idispatch.core.client.support.grpc.SubjectAwareCallCredentials;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Implementation of {@link AuthenticationClient} that uses gRPC to contact a server that performs the authentication.
 */
public final class GrpcAuthenticationClient extends GrpcServiceClient implements AuthenticationClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcAuthenticationClient.class);
    private final SecureRandom secureRandom;
    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub server;

    public GrpcAuthenticationClient(@NotNull GrpcContext grpcContext) {
        super(grpcContext);
        LOGGER.info("Initializing authentication client");
        server = AuthenticationServiceGrpc.newBlockingStub(grpcContext.channel())
                .withCallCredentials(SubjectAwareCallCredentials.getInstance())
                .withWaitForReady();
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Cannot initialize client", ex);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P extends AuthenticationProcess<S>, S extends AuthenticatedSession>
    @NotNull P startAuthentication(@NotNull AuthenticationRequest<P, S> authenticationRequest) {
        LOGGER.debug("Received authentication request [{}]", authenticationRequest);
        if (authenticationRequest instanceof UserAuthenticationRequest) {
            return (P) startAuthentication((UserAuthenticationRequest) authenticationRequest);
        } else if (authenticationRequest instanceof DeviceAuthenticationRequest) {
            return (P) startAuthentication((DeviceAuthenticationRequest) authenticationRequest);
        }

        LOGGER.error("Unsupported authentication request [{}]", authenticationRequest);
        throw new IllegalArgumentException("Unsupported authentication request");
    }

    private @NotNull GrpcUserAuthenticationProcess startAuthentication(
            @NotNull UserAuthenticationRequest authenticationRequest) {
        return new GrpcUserAuthenticationProcess(server, authenticationRequest);
    }

    private @NotNull GrpcDeviceAuthenticationProcess startAuthentication(
            @NotNull DeviceAuthenticationRequest authenticationRequest) {
        return new GrpcDeviceAuthenticationProcess(server, secureRandom, authenticationRequest);
    }
}
