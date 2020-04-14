package net.pkhapps.idispatch.core.auth.server.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.pkhapps.idispatch.core.auth.proto.*;
import net.pkhapps.idispatch.core.auth.server.application.AuthenticationService;
import net.pkhapps.idispatch.core.auth.server.application.PasswordService;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * TODO Implement me and Document me
 */
public class AuthenticationServiceAdapter extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {

    private final AuthenticationService authenticationService;
    private final PasswordService passwordService;

    public AuthenticationServiceAdapter(@NotNull AuthenticationService authenticationService,
                                        @NotNull PasswordService passwordService) {
        this.authenticationService = requireNonNull(authenticationService);
        this.passwordService = requireNonNull(passwordService);
    }

    @Override
    public void initUserAuthentication(UserAuthenticationRequest request, StreamObserver<UserAuthenticationChallenge> responseObserver) {
        super.initUserAuthentication(request, responseObserver);
    }

    @Override
    public void completeUserAuthentication(AuthenticationResponse request, StreamObserver<UserAuthenticationOutcome> responseObserver) {
        super.completeUserAuthentication(request, responseObserver);
    }

    @Override
    public void initDeviceAuthentication(DeviceAuthenticationRequest request, StreamObserver<DeviceAuthenticationChallenge> responseObserver) {
        super.initDeviceAuthentication(request, responseObserver);
    }

    @Override
    public void completeDeviceAuthentication(AuthenticationResponse request, StreamObserver<DeviceAuthenticationOutcome> responseObserver) {
        super.completeDeviceAuthentication(request, responseObserver);
    }

    @Override
    public void refreshToken(Empty request, StreamObserver<TokenRefreshOutcome> responseObserver) {
        super.refreshToken(request, responseObserver);
    }

    @Override
    public void invalidateToken(Empty request, StreamObserver<Empty> responseObserver) {
        super.invalidateToken(request, responseObserver);
    }

    @Override
    public void changePassword(PasswordChangeRequest request, StreamObserver<PasswordChangeOutcome> responseObserver) {
        var currentPasswordHash = request.getCurrentPasswordHash();
        var newPasswordHash = request.getNewPasswordHash();
        passwordService.changePasswordForCurrentPrincipal(currentPasswordHash, newPasswordHash);
        //responseObserver.onNext(PasswordChangeOutcome.newBuilder().setError().build());
    }
}
