package net.pkhapps.idispatch.core.auth.client.grpc;

import net.pkhapps.idispatch.core.auth.client.api.user.AuthenticatedUserSession;
import net.pkhapps.idispatch.core.auth.client.api.user.UserAuthenticationProcess;
import net.pkhapps.idispatch.core.auth.client.api.user.UserAuthenticationRequest;
import net.pkhapps.idispatch.core.auth.proto.AuthenticationServiceGrpc;
import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me
 */
class GrpcUserAuthenticationProcess implements UserAuthenticationProcess {

    GrpcUserAuthenticationProcess(@NotNull AuthenticationServiceGrpc.AuthenticationServiceBlockingStub server,
                                  @NotNull UserAuthenticationRequest authenticationRequest) {

    }

    @Override
    public boolean requiresTwoFactorAuthentication() {
        return false;
    }

    @Override
    public void setUserPassword(@NotNull String password) {

    }

    @Override
    public void setOneTimePassword(@NotNull String oneTimePassword) {

    }

    @NotNull
    @Override
    public AuthenticatedUserSession authenticate() {
        return null;
    }
}
