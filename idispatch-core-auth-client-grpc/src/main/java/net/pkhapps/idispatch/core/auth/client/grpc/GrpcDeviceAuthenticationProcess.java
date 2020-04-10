package net.pkhapps.idispatch.core.auth.client.grpc;

import com.google.protobuf.ByteString;
import net.pkhapps.idispatch.core.auth.client.api.*;
import net.pkhapps.idispatch.core.auth.client.api.device.DeviceAuthenticationProcess;
import net.pkhapps.idispatch.core.auth.client.api.device.DeviceAuthenticationRequest;
import net.pkhapps.idispatch.core.auth.proto.AuthenticationResponse;
import net.pkhapps.idispatch.core.auth.proto.AuthenticationServiceGrpc;
import net.pkhapps.idispatch.core.auth.proto.DeviceAuthenticationChallenge;
import net.pkhapps.idispatch.core.auth.proto.DeviceAuthenticationOutcome;
import net.pkhapps.idispatch.core.client.support.api.MultilingualString;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link DeviceAuthenticationProcess} that uses gRPC to complete the authentication.
 */
final class GrpcDeviceAuthenticationProcess implements DeviceAuthenticationProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcDeviceAuthenticationProcess.class);
    private static final String HASH_ALGORITHM = "SHA-256";
    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub server;
    private final AtomicLong nextSeqNo;
    private final DeviceAuthenticationChallenge challenge;

    private String secret;

    GrpcDeviceAuthenticationProcess(@NotNull AuthenticationServiceGrpc.AuthenticationServiceBlockingStub server,
                                    @NotNull SecureRandom secureRandom,
                                    @NotNull DeviceAuthenticationRequest authenticationRequest) {
        this.server = requireNonNull(server);
        nextSeqNo = new AtomicLong(secureRandom.nextLong());
        LOGGER.debug("Initializing device authentication for device [{}] and tenant [{}]",
                authenticationRequest.deviceId(), authenticationRequest.tenantId());
        challenge = server.initDeviceAuthentication(net.pkhapps.idispatch.core.auth.proto.DeviceAuthenticationRequest
                .newBuilder()
                .setSeqNo(nextSeqNo.getAndIncrement())
                .setTenantId(authenticationRequest.tenantId())
                .setDeviceId(authenticationRequest.deviceId())
                .build());
        LOGGER.debug("Received challenge from server with conversation number [{}]", challenge.getConversationNo());
        verifySequenceNumber(challenge.getSeqNo());
    }

    @Override
    public void setDeviceSecret(@NotNull String secret) {
        this.secret = requireNonNull(secret);
    }

    @NotNull
    @Override
    public AuthenticatedSession authenticate() {
        var response = server.completeDeviceAuthentication(AuthenticationResponse.newBuilder()
                .setSeqNo(nextSeqNo.getAndIncrement())
                .setResponse(ByteString.copyFrom(computeResponseHash()))
                .setConversationNo(challenge.getConversationNo())
                .build());
        LOGGER.debug("Received authentication response [{}] from server", response.getStatus());
        verifySequenceNumber(response.getSeqNo());

        switch (response.getStatus()) {
            case SUCCESS:
                return createSession(response);
            case INVALID_CREDENTIALS:
                throw createInvalidCredentialsException();
            case INVALID_PRINCIPAL:
                throw createInvalidPrincipalException();
            case ACCOUNT_LOCKED:
                throw createAccountLockedException();
            default:
                throw new InternalAuthenticationException("Unknown response code: " + response.getStatus());
        }
    }

    private @NotNull AuthenticatedSession createSession(@NotNull DeviceAuthenticationOutcome outcome) {
        return new GrpcAuthenticatedDeviceSession(server, outcome);
    }

    private @NotNull InvalidCredentialsException createInvalidCredentialsException() {
        return new InvalidCredentialsException(new MultilingualString(
                "Antamasi laitesalasana ei kelpaa",
                "Enhetslösenordet som du angett duger inte"
        ));
    }

    private @NotNull InvalidPrincipalException createInvalidPrincipalException() {
        return new InvalidPrincipalException(new MultilingualString(
                "Antamasi laitetunniste ei kelpaa",
                "Enhetsidentifikationen som du angett duger inte"
        ));
    }

    private @NotNull AccountLockedException createAccountLockedException() {
        return new AccountLockedException(new MultilingualString(
                "Laite on lukittu",
                "Enheten är låst"
        ));
    }

    private byte[] computeResponseHash() {
        LOGGER.debug("Computing response hash");
        if (secret == null) {
            throw new IllegalStateException("No secret has been set");
        }
        try {
            var digest = MessageDigest.getInstance(HASH_ALGORITHM);

            // First compute a hash of the secret since the server will only have the hash to compare with
            var hashedSecret = digest.digest(secret.getBytes(StandardCharsets.UTF_8));

            // Then compute the response hash based on the challenge received by the server
            digest.update(hashedSecret);
            digest.update(challenge.getChallenge().toByteArray());
            return digest.digest();
        } catch (NoSuchAlgorithmException ex) {
            throw new InternalAuthenticationException("Could not compute response hash: " + ex.getMessage());
        }
    }

    private void verifySequenceNumber(long actualSequenceNumber) {
        LOGGER.debug("Verifying sequence number {}", actualSequenceNumber);
        if (actualSequenceNumber != nextSeqNo.getAndIncrement()) {
            throw new InternalAuthenticationException("Received invalid sequence number");
        }
    }
}
