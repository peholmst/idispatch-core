package net.pkhapps.idispatch.core.auth.client.grpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import io.grpc.BindableService;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import net.pkhapps.idispatch.core.auth.client.api.AuthenticationClient;
import net.pkhapps.idispatch.core.auth.client.api.device.DeviceAuthenticationRequest;
import net.pkhapps.idispatch.core.auth.proto.*;
import net.pkhapps.idispatch.core.client.support.grpc.GrpcContext;
import net.pkhapps.idispatch.core.client.support.grpc.testing.AbstractGrpcTest;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link GrpcAuthenticationClient} that uses a mock gRPC service implementation to test the device
 * authentication process.
 */
public class GrpcDeviceAuthenticationProcessTest extends AbstractGrpcTest {

    private static final String TENANT_ID = "myTenant";
    private static final String DEVICE_ID = "myDevice";
    private static final String DEVICE_SECRET = "mySecret";
    private static final String DEVICE_NAME = "My Device";
    private static final Instant TOKEN_VALID_FROM = Instant.now();
    private static final Instant TOKEN_VALID_TO = TOKEN_VALID_FROM.plusSeconds(60 * 60 * 24);
    private static final String AUTHORITY1 = "AUTH_FOO";
    private static final String AUTHORITY2 = "AUTH_BAR";

    private final AuthenticationServiceGrpc.AuthenticationServiceImplBase serviceImpl = new AuthenticationServiceGrpc.AuthenticationServiceImplBase() {

        private final AtomicLong nextConversation = new AtomicLong();
        private final Random random = new Random();
        private final Map<Long, Conversation> conversations = new ConcurrentHashMap<>();

        @Override
        public void initDeviceAuthentication(net.pkhapps.idispatch.core.auth.proto.DeviceAuthenticationRequest request,
                                             StreamObserver<DeviceAuthenticationChallenge> responseObserver) {
            var conversation = new Conversation(request.getTenantId(), request.getDeviceId(), request.getSeqNo());
            conversations.put(conversation.conversationNo(), conversation);
            responseObserver.onNext(conversation.challenge());
            responseObserver.onCompleted();
        }

        @Override
        public void completeDeviceAuthentication(AuthenticationResponse request,
                                                 StreamObserver<DeviceAuthenticationOutcome> responseObserver) {
            try {
                var conversation = conversations.remove(request.getConversationNo());
                if (conversation == null) {
                    throw new IllegalStateException("Unknown conversation");
                }
                responseObserver.onNext(conversation.response(request.getResponse(), request.getSeqNo()));
                responseObserver.onCompleted();
            } catch (Exception ex) {
                responseObserver.onError(ex);
            }
        }

        class Conversation {
            private final String tenantId;
            private final String deviceId;
            private final AtomicLong seqNo;
            private final long conversationNo;
            private final byte[] challenge;
            private final byte[] token;

            Conversation(@NotNull String tenantId, @NotNull String deviceId, long seqNo) {
                this.tenantId = tenantId;
                this.deviceId = deviceId;
                this.seqNo = new AtomicLong(seqNo);
                this.conversationNo = nextConversation.getAndIncrement();
                this.challenge = new byte[8];
                random.nextBytes(this.challenge);
                this.token = new byte[16];
                random.nextBytes(this.token);
            }

            public long conversationNo() {
                return conversationNo;
            }

            @NotNull DeviceAuthenticationChallenge challenge() {
                return DeviceAuthenticationChallenge.newBuilder()
                        .setConversationNo(conversationNo)
                        .setSeqNo(seqNo.incrementAndGet())
                        .setChallenge(ByteString.copyFrom(challenge))
                        .build();
            }

            @NotNull DeviceAuthenticationOutcome response(@NotNull ByteString response, long seqNo) throws Exception {
                if (this.seqNo.incrementAndGet() != seqNo) {
                    throw new IllegalStateException("Invalid sequence number");
                }
                var digest = MessageDigest.getInstance("SHA-256");
                var hashedSecret = digest.digest(DEVICE_SECRET.getBytes(StandardCharsets.UTF_8));
                digest.update(hashedSecret);
                digest.update(challenge);
                var hash = digest.digest();
                var status = AuthenticationStatusCode.SUCCESS;
                if (!tenantId.equals(TENANT_ID) || !deviceId.equals(DEVICE_ID)) {
                    status = AuthenticationStatusCode.INVALID_PRINCIPAL;
                } else if (!Arrays.equals(hash, response.toByteArray())) {
                    status = AuthenticationStatusCode.INVALID_CREDENTIALS;
                }
                var builder = DeviceAuthenticationOutcome.newBuilder()
                        .setConversationNo(conversationNo)
                        .setSeqNo(this.seqNo.incrementAndGet())
                        .setStatus(status);
                if (status == AuthenticationStatusCode.SUCCESS) {
                    builder.setPrincipal(DevicePrincipal.newBuilder()
                            .setDeviceId(DEVICE_ID)
                            .setTenantId(TENANT_ID)
                            .setDisplayName(DEVICE_NAME)
                            .addAuthority(AUTHORITY1)
                            .build());
                    builder.setToken(AuthenticationToken.newBuilder()
                            .setToken(ByteString.copyFrom(token))
                            .setTokenValidFrom(Timestamp.newBuilder()
                                    .setSeconds(TOKEN_VALID_FROM.getEpochSecond())
                                    .setNanos(TOKEN_VALID_FROM.getNano())
                                    .build())
                            .setTokenValidTo(Timestamp.newBuilder()
                                    .setSeconds(TOKEN_VALID_TO.getEpochSecond())
                                    .setNanos(TOKEN_VALID_TO.getNano())
                                    .build())
                            .build());
                }
                return builder.build();
            }
        }
    };

    private AuthenticationClient client;

    @Override
    protected @NotNull Stream<BindableService> setUpServerSide() throws Exception {
        return Stream.of(serviceImpl);
    }

    @Override
    protected void setUpClientSide(@NotNull Channel channel) throws Exception {
        client = new GrpcAuthenticationClient(new GrpcContext(channel));
    }

    @Test
    public void deviceAuthentication_successful() {
        var process = client.startAuthentication(new DeviceAuthenticationRequest(TENANT_ID, DEVICE_ID));
        process.setDeviceSecret(DEVICE_SECRET);
        var session = process.authenticate();
        var principal = session.principal();

        assertThat(session.isValid()).isTrue();
        assertThat(principal.implies(session.subject())).isTrue();
        assertThat(principal.displayName()).isEqualTo(DEVICE_NAME);
        assertThat(principal.tenantId()).isEqualTo(TENANT_ID);
        assertThat(principal.getName()).isEqualTo(DEVICE_ID);
        assertThat(principal.hasAuthority(AUTHORITY1)).isTrue();
        assertThat(principal.hasAuthority(AUTHORITY2)).isFalse();
        assertThat(session.expiresOn()).isEqualTo(TOKEN_VALID_TO);
    }
}
