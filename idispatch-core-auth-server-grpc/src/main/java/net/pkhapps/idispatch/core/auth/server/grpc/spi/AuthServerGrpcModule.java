package net.pkhapps.idispatch.core.auth.server.grpc.spi;

import io.grpc.BindableService;
import net.pkhapps.idispatch.core.auth.server.AuthModule;
import net.pkhapps.idispatch.core.auth.server.grpc.AuthenticationServiceAdapter;
import net.pkhapps.idispatch.core.server.support.grpc.spi.GrpcModule;
import net.pkhapps.idispatch.core.server.support.spi.Modules;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/**
 * TODO Document me
 */
public class AuthServerGrpcModule implements GrpcModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServerGrpcModule.class);

    private final AuthenticationServiceAdapter authenticationServiceAdapter;

    public AuthServerGrpcModule() {
        LOGGER.info("Initializing gRPC adapter for AuthModule");
        var authModule = Modules.require(AuthModule.class);
        authenticationServiceAdapter = new AuthenticationServiceAdapter(authModule.authenticationService(),
                authModule.passwordService());
    }

    @Override
    public @NotNull Stream<BindableService> services() {
        return Stream.of(authenticationServiceAdapter);
    }
}
